@echo off

cd cassandragateway
start "Keycloak" docker compose -f src\main\docker\keycloak.yml up -d
start "JHipster Registry" docker compose -f src\main\docker\jhipster-registry.yml up -d
cd ..

cd cassandragateway
call npm run docker:db:up
start "Gateway" mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev
cd ..

cd cassandrablog
call npm run docker:db:up
echo Deploying Blog Service...
start "Blog" mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev
cd ..

cd cassandrastore
call npm run docker:db:up
echo Deploying Store Service...
start "Store" mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev
cd ..
