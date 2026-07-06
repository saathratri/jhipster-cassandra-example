@echo off

echo Compiling Blog
cd cassandrablog
call mvnw.cmd clean package -Pdev,api-docs -DskipTests
cd ..

echo Compiling Store
cd cassandrastore
call mvnw.cmd clean package -Pdev,api-docs -DskipTests
cd ..

echo Compiling Gateway
cd cassandragateway
call mvnw.cmd clean package -Pdev,api-docs -DskipTests
cd ..

echo Completed compilation of Saathratri Development Version
