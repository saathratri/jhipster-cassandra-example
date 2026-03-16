cd cassandragateway
ttab docker compose -f src/main/docker/keycloak.yml up -d
ttab docker compose -f src/main/docker/jhipster-registry.yml up -d
cd ..

cd cassandragateway
npm run docker:db:up
ttab ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
cd ..

cd cassandrablog
npm run docker:db:up
echo "Deploying Blog Service..."
ttab ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
cd ..

cd cassandrastore
npm run docker:db:up
echo "Deploying Store Service..."
ttab ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
cd ..
