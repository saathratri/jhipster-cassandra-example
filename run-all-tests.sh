#!/usr/bin/env bash
# =============================================================================
# run-all-tests.sh — reliable one-shot full test run for the cassandra example
#
# Runs, in order, for all 3 apps (cassandragateway / cassandrablog / cassandrastore):
#   1. (optional) regen from the blueprint        --regen
#   2. backend unit + integration (mvnw verify)
#   3. frontend (vitest, ng test)
#   4. webapp build into target/classes/static    (required for e2e index.html)
#   5. launch the 3 backends once, wait for a REAL readiness gate
#   6. e2e (Cypress) per app, with a whole-suite retry on cold-load flake
#   7. tear down the 3 backends (Docker stack left running)
#
# Why this is a reliable one-shot (vs. running the npm scripts by hand):
#   - The store (single-key-only) microfrontend list GET can fail to fire if the
#     gateway hasn't finished registering the remote's Eureka route when Cypress
#     starts ("cy.wait('@entitiesRequest') ... No request ever occurred"). Cypress
#     in-spec retries (already 5) don't help — the route is down for the whole
#     window. This script gates e2e on the gateway->remote ROUTE being live (not
#     just app health), and retries the whole suite once if it still flakes.
#   - e2e:headless is broken on Windows (unexpanded $npm_package_config_backend_port);
#     this calls `npm run e2e:cypress` against the already-running stack.
#
# Usage:
#   sh run-all-tests.sh                # full run, no regen
#   sh run-all-tests.sh --regen        # regen first, then full run
#   sh run-all-tests.sh --skip-backend --skip-frontend   # e2e only (stack rebuilt)
#   sh run-all-tests.sh --no-e2e       # backend + frontend only
#
# Requires: Docker daemon running (Cassandra/Postgres/Keycloak/Registry stack +
# Testcontainers), JDK 21, Node 22+, the blueprint npm-linked (for --regen).
# =============================================================================
set -u

# ---- config ---------------------------------------------------------------
APPS="cassandragateway cassandrablog cassandrastore"
# app -> server port (NOT the 934x/924x Cassandra CQL docker ports)
declare -A PORT=( [cassandragateway]=8080 [cassandrablog]=8081 [cassandrastore]=8082 )
# remotes whose gateway route must be live before e2e (gateway has no remote of its own)
declare -A PROBE=( [cassandrablog]="services/cassandrablog/api/tags" [cassandrastore]="services/cassandrastore/api/products" )

export NODE_OPTIONS=--use-system-ca
export MAVEN_OPTS="-Djavax.net.ssl.trustStoreType=Windows-ROOT"

ROOT="$(cd "$(dirname "$0")" && pwd)"
LOG="$ROOT/.test-logs"; mkdir -p "$LOG"
cd "$ROOT"

REGEN=0; SKIP_BE=0; SKIP_FE=0; DO_E2E=1
for a in "$@"; do case "$a" in
  --regen) REGEN=1;; --skip-backend) SKIP_BE=1;; --skip-frontend) SKIP_FE=1;;
  --no-e2e) DO_E2E=0;; *) echo "unknown arg: $a"; exit 2;; esac; done

# result accumulators
declare -A R_BE_UNIT R_BE_IT R_FE R_E2E
FAIL=0
hr(){ printf '%.0s─' $(seq 1 70); echo; }
say(){ echo; hr; echo ">>> $*"; hr; }

# ---- 0. clean slate: kill any app backends from a prior run ---------------
kill_backends() {
  # Kill example backends by command-line match AND by whoever currently holds 8080/8081/8082.
  # The command-line match alone misses nothing of ours, but belt-and-suspenders we also free the
  # ports — the real root cause of the store "flake" was a freshly launched gateway colliding with
  # a straggler on 8080 and dying with "Port 8080 was already in use", which took the e2e run down.
  powershell.exe -NoProfile -Command "
    Get-CimInstance Win32_Process -Filter \"Name='java.exe'\" |
      Where-Object { \$_.CommandLine -match 'jhipster-cassandra-example' -or \$_.CommandLine -match 'Cassandra(gateway|blog|store)App' } |
      ForEach-Object { try { Stop-Process -Id \$_.ProcessId -Force -ErrorAction Stop } catch {} }
    foreach (\$p in 8080,8081,8082) {
      Get-NetTCPConnection -State Listen -LocalPort \$p -ErrorAction SilentlyContinue |
        ForEach-Object { try { Stop-Process -Id \$_.OwningProcess -Force -ErrorAction Stop } catch {} }
    }" >/dev/null 2>&1 || true
}

# Block until 8080/8081/8082 have no listener (max ~60s). Echoes status; returns 0 when free.
wait_ports_free() {
  for i in $(seq 1 30); do
    busy=$(powershell.exe -NoProfile -Command "(Get-NetTCPConnection -State Listen -LocalPort 8080,8081,8082 -ErrorAction SilentlyContinue | Measure-Object).Count" 2>/dev/null | tr -dc '0-9')
    [ "${busy:-0}" = "0" ] && return 0
    sleep 2
  done
  echo "  ⚠ ports still busy after wait: ${busy:-?} listener(s)"; return 1
}
say "Clean slate: stopping any running example backends + freeing ports"
kill_backends
wait_ports_free || { echo "  could not free 8080/8081/8082 — aborting"; exit 1; }
echo "  ports 8080/8081/8082 free"

# ---- 1. regen (optional) --------------------------------------------------
if [ "$REGEN" = 1 ]; then
  say "Regenerating from blueprint"
  jhipster --blueprints cassandra jdl saathratri-apps-cassandra-mf.jdl \
    --monorepository --workspaces --skip-jhipster-dependencies --force > "$LOG/regen.log" 2>&1
  if ! grep -qa "execution is complete" "$LOG/regen.log"; then
    echo "REGEN FAILED — see $LOG/regen.log"; tail -15 "$LOG/regen.log"; exit 1
  fi
  echo "regen OK"
fi

# ---- 2. backend unit + IT -------------------------------------------------
if [ "$SKIP_BE" = 0 ]; then
  for app in cassandrastore cassandrablog cassandragateway; do
    say "Backend (unit + IT): $app"
    ( cd "$app" && ./mvnw -ntp -Dskip.npm clean verify ) > "$LOG/be-$app.log" 2>&1
    ok=$?
    # first aggregate 'Tests run' = surefire(unit), second = failsafe(IT)
    mapfile -t totals < <(grep -aE "^\[INFO\] Tests run: [0-9]+, Failures: [0-9]+, Errors: [0-9]+, Skipped: [0-9]+$" "$LOG/be-$app.log")
    R_BE_UNIT[$app]="${totals[0]:-(none)}"
    R_BE_IT[$app]="${totals[1]:-(none)}"
    if [ $ok -ne 0 ] || ! grep -qa "BUILD SUCCESS" "$LOG/be-$app.log"; then
      echo "  ✖ $app backend FAILED (see $LOG/be-$app.log)"; FAIL=1
    else echo "  ✔ $app backend OK"; fi
  done
fi

# ---- 3. frontend (vitest) -------------------------------------------------
if [ "$SKIP_FE" = 0 ]; then
  for app in $APPS; do
    say "Frontend (vitest): $app"
    ( cd "$app" && npx ng test --watch=false ) > "$LOG/fe-$app.log" 2>&1
    ok=$?
    R_FE[$app]="$(sed -E 's/\x1b\[[0-9;]*m//g' "$LOG/fe-$app.log" | grep -aE 'Tests +[0-9]+ (passed|failed)' | tail -1 | sed 's/^[[:space:]]*//')"
    if [ $ok -ne 0 ]; then echo "  ✖ $app frontend FAILED (see $LOG/fe-$app.log)"; FAIL=1
    else echo "  ✔ $app frontend OK"; fi
  done
fi

# ---- 4-6. e2e -------------------------------------------------------------
if [ "$DO_E2E" = 1 ]; then
  http(){ curl -s -o /dev/null -w '%{http_code}' --max-time 6 "$1" 2>/dev/null; }

  # Pre-flight: this harness does NOT start infra — it assumes the Docker stack
  # (Cassandra/Postgres + Keycloak + JHipster Registry) is already up (see header). If the
  # Registry isn't live the remotes can't register, the gateway gets no routes, and every
  # entity e2e 404s after a long, confusing wait; if Keycloak isn't serving OIDC the Registry
  # itself crashes on boot. Verify both NOW and fail fast (before ~5 min of webapp builds +
  # backend launches) with an actionable message instead of a late, opaque failure.
  say "Pre-flight: infra readiness (Keycloak OIDC :9080 + JHipster Registry :8761)"
  printf "  Keycloak OIDC (:9080) "
  kc=$(http "http://localhost:9080/realms/jhipster/.well-known/openid-configuration"); echo "$kc"
  [ "$kc" = 200 ] || { echo "  ✖ Keycloak not serving OIDC — start the docker stack first (saathratri-deploy.sh); aborting e2e"; FAIL=1; }
  printf "  JHipster Registry (:8761) "
  rg=$(http "http://localhost:8761/management/health"); echo "$rg"
  case "$rg" in 200|401) ;; *) echo "  ✖ Registry not live on :8761 (got $rg) — remotes can't register; start the stack first; aborting e2e"; FAIL=1;; esac
  if [ "$FAIL" = 1 ]; then echo "  infra not ready — skipping e2e"; DO_E2E=0; fi
fi

if [ "$DO_E2E" = 1 ]; then
  say "Building webapps into target/classes/static (required for e2e)"
  for app in $APPS; do
    ( cd "$app" && npm run webapp:build ) > "$LOG/web-$app.log" 2>&1 || { echo "  ✖ $app webapp build FAILED"; FAIL=1; }
    [ -f "$app/target/classes/static/index.html" ] && echo "  ✔ $app index.html" || { echo "  ✖ $app index.html MISSING"; FAIL=1; }
  done

  say "Ensuring ports free before launching backends"
  kill_backends; wait_ports_free || { echo "  ports busy — aborting e2e"; FAIL=1; }

  say "Launching 3 backends (single job)"
  ( cd cassandragateway && ./mvnw -ntp -Dskip.npm spring-boot:run -Dspring-boot.run.profiles=dev > "$LOG/run-gateway.log" 2>&1 ) &
  ( cd cassandrablog   && ./mvnw -ntp -Dskip.npm spring-boot:run -Dspring-boot.run.profiles=dev > "$LOG/run-blog.log"    2>&1 ) &
  ( cd cassandrastore  && ./mvnw -ntp -Dskip.npm spring-boot:run -Dspring-boot.run.profiles=dev > "$LOG/run-store.log"   2>&1 ) &

  say "Readiness gate: app health (808x) + gateway->remote routes"
  # 6a. every app server up on its real port
  for app in $APPS; do
    p=${PORT[$app]}
    printf "  waiting %s health :%s " "$app" "$p"
    for i in $(seq 1 60); do [ "$(http http://localhost:$p/management/health)" = 200 ] && break; sleep 5; printf '.'; done
    code=$(http http://localhost:$p/management/health)
    echo " $code"; [ "$code" = 200 ] || { echo "  ✖ $app never became healthy"; FAIL=1; }
  done
  # 6b. gateway serves the SPA shell
  printf "  gateway root (index.html): "; echo "$(http http://localhost:8080/)"
  # 6c. THE key gate: gateway->remote route live (302 OAuth redirect / 200 / 401, NOT 404/000/5xx).
  #     This is what was missing — e2e starting before the gateway registers the remote's
  #     Eureka route is exactly the store flake.
  for app in cassandrablog cassandrastore; do
    probe="http://localhost:8080/${PROBE[$app]}"
    printf "  waiting gateway->%s route " "$app"
    for i in $(seq 1 60); do
      c=$(http "$probe"); case "$c" in 200|301|302|401) break;; esac; sleep 5; printf '.'
    done
    c=$(http "$probe"); echo " $c"
    case "$c" in 200|301|302|401) ;; *) echo "  ✖ gateway->$app route not live ($c)"; FAIL=1;; esac
  done
  echo "  letting federation settle 20s"; sleep 20

  # 6d. run each app's Cypress suite ONCE. Pass/fail is the cypress EXIT CODE (non-zero on any
  # failed spec) — not log-grepping, which previously reported a pass while the run had failed.
  # No whole-suite retry: with the hardened port-clearing above, the prior "flake" (a crashed
  # gateway from a port collision) is gone, so a true one-shot must pass in one attempt; a retry
  # would only mask a real regression.
  for app in $APPS; do
    say "E2E (Cypress): $app"
    ( cd "$app" && npm run e2e:cypress ) > "$LOG/e2e-$app.log" 2>&1
    rc=$?
    summary="$(grep -aE 'All specs passed|[0-9]+ of [0-9]+ failed' "$LOG/e2e-$app.log" | tr -d '\033' | sed -E 's/\x1b\[[0-9;]*m//g; s/^[[:space:]]*//' | tail -1)"
    if [ $rc -eq 0 ]; then
      R_E2E[$app]="PASS — ${summary:-(no summary line)}"
      echo "  ✔ $app e2e passed"
    else
      R_E2E[$app]="FAIL(rc=$rc) — ${summary:-(no cypress summary; see log)}"
      echo "  ✖ $app e2e FAILED (rc=$rc, see $LOG/e2e-$app.log)"; FAIL=1
    fi
  done

  say "Tearing down backends (Docker stack left running)"
  kill_backends
fi

# ---- report ---------------------------------------------------------------
say "REPORT"
printf "%-20s | %-34s | %-34s | %-26s | %s\n" "APP" "BACKEND unit" "BACKEND IT" "FRONTEND" "E2E"
for app in $APPS; do
  printf "%-20s | %-34s | %-34s | %-26s | %s\n" "$app" \
    "${R_BE_UNIT[$app]:-skipped}" "${R_BE_IT[$app]:-skipped}" "${R_FE[$app]:-skipped}" "${R_E2E[$app]:-skipped}"
done
echo
if [ "$FAIL" = 0 ]; then echo "✅ ALL GREEN"; else echo "❌ FAILURES — inspect $LOG/*.log"; fi
echo "logs: $LOG"
exit $FAIL
