@echo off
REM Bring up the full Cassandra example stack, each service in its own
REM Windows Terminal tab (mirrors the .sh version that uses `ttab`).
REM Requires Windows Terminal (`wt.exe`) — installed by default on Windows 11.

cd cassandragateway
wt -w 0 new-tab --title "Keycloak" -d "%cd%" cmd /k "docker compose -f src\main\docker\keycloak.yml up -d"
wt -w 0 new-tab --title "JHipster Registry" -d "%cd%" cmd /k "docker compose -f src\main\docker\jhipster-registry.yml up -d"
cd ..

cd cassandragateway
call npm run docker:db:up
wt -w 0 new-tab --title "Gateway" -d "%cd%" cmd /k "mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev"
cd ..

cd cassandrablog
call npm run docker:db:up
echo Deploying Blog Service...
wt -w 0 new-tab --title "Blog" -d "%cd%" cmd /k "mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev"
cd ..

cd cassandrastore
call npm run docker:db:up
echo Deploying Store Service...
wt -w 0 new-tab --title "Store" -d "%cd%" cmd /k "mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev"
cd ..
