echo "Compiling Blog"
cd cassandrablog
./mvnw clean package -Pdev,api-docs -DskipTests
cd ..

echo "Compiling Store"
cd cassandrastore
./mvnw clean package -Pdev,api-docs -DskipTests
cd ..

echo "Compiling Gateway"
cd cassandragateway
./mvnw clean package -Pdev,api-docs -DskipTests
cd ..

echo "Completed compilation of Saathratri Development Version"
