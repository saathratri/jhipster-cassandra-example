@echo off
echo Cleaning up the dev environment for Saathratri Cassandra
if exist .yo-rc.json del /q .yo-rc.json
if exist cassandragateway rmdir /s /q cassandragateway
if exist cassandrablog rmdir /s /q cassandrablog
if exist cassandrastore rmdir /s /q cassandrastore
if exist docker-compose rmdir /s /q docker-compose
if exist kubernetes rmdir /s /q kubernetes
if exist node_modules rmdir /s /q node_modules
if exist package.json del /q package.json
if exist package-lock.json del /q package-lock.json
