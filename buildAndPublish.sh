#!/bin/sh
set -e
export JAVA_HOME='c:\workspace\tools\jdk-21.0.1+12\'

./gradlew :products:bootJar
# ./gradlew :products:bootBuildImage --imageName=localhost:5000/services/products:latest
docker build -t localhost:5000/services/products:latest ./products
docker push localhost:5000/services/products:latest

./gradlew :customers:bootJar
docker build -t localhost:5000/services/customers:latest ./customers
docker push localhost:5000/services/customers:latest
#
./gradlew :main:bootJar
docker build -t localhost:5000/services/main:latest ./main
docker push localhost:5000/services/main:latest