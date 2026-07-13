#!/bin/sh

export JAVA_HOME='/usr/java/21'
export IMAGE_ROOT_PATH='localhost:5000/services'

build() {
  projects=$(gum choose --no-limit "products" "customers" "categories" "main")
  for project in $projects; do
    echo "Building $project"
    ./gradlew :$project:bootJar
    docker build -t "$IMAGE_ROOT_PATH/$project:latest" ./$project
    docker push "$IMAGE_ROOT_PATH/$project:latest"
  done
}

deploy() {
  project=$(gum choose "products" "customers" "categories" "main")
  export NAME=$project
  export DEPLOY=$(date '+%Y%m%d:%H%M%S')
  envsubst < apps/service.yaml | kubectl apply -f -
  unset NAME
  unset DEPLOY
}