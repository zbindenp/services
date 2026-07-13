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
  project="${1:-$(gum choose "products" "customers" "categories" "main")}"
  export NAME=$project
  export DEPLOY=$(date '+%Y%m%d:%H%M%S')
  envsubst < apps/service.yaml | kubectl apply -f -
  unset NAME
  unset DEPLOY
}

services() {
  projects="${2:-$(gum choose --no-limit "products" "customers" "categories" "main")}"
  case "$1" in
  build)
    for project in $projects; do
      echo "Building $project"
      ./gradlew :$project:bootJar
    done
    ;;
  push)
    for project in $projects; do
      echo "Pushing image $project"
      docker build -t "$IMAGE_ROOT_PATH/$project:latest" ./$project
      docker push "$IMAGE_ROOT_PATH/$project:latest"
    done
    ;;
  deploy)
    if [[ -z "${SA_PASSWORD}" ]]; then
      echo "env SA_PASSWORD must be set"
      return 1
    fi
    for project in $projects; do
      echo "Deploying $project"
      export DEPLOY=$(date '+%Y%m%d:%H%M%S')
      envsubst < "$project/service.yaml" | kubectl apply -f -
      unset DEPLOY
    done
    ;;
    * )
      echo "services [build|push\deploy]"
  esac

}