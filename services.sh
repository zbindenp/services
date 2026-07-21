#!/bin/bash

select_projects() {
  if [[ "${1}" ]]; then
    echo $1
  else
    echo $(gum choose --no-limit  products customers categories main)
  fi
}

_init() {
  echo "select JAVA_HOME"
  JAVA_HOME=$(gum file ${JAVA_HOME}/.. --directory)
  echo "Set Docker Registry to push"
  DOCKER_REGISTRY_PUSH=$(gum input --placeholder "Docker Registry tp push" --value="${DOCKER_REGISTRY_PUSH:-"localhost:5000"}")
  echo "Set Docker Registry to pull"
  DOCKER_REGISTRY_PULL=$(gum input --placeholder "Docker Registry tp pull" --value="${DOCKER_REGISTRY_PULL:-${DOCKER_REGISTRY_PUSH}}")
  echo "Set Test Domain"
  TEST_DOMAIN=$(gum input --placeholder "Test Domain" --value="${TEST_DOMAIN:-"example.com"}")
  echo "Writing env to [./.env]"
  echo "JAVA_HOME=${JAVA_HOME}" > "./.env"
  echo "DOCKER_REGISTRY_PUSH=${DOCKER_REGISTRY_PUSH}" >> "./.env"
  echo "DOCKER_REGISTRY_PULL=${DOCKER_REGISTRY_PULL}" >> "./.env"
  echo "TEST_DOMAIN=${TEST_DOMAIN}" >> "./.env"
}

services() {
  if [[ -e "./.env" ]]; then
    echo "env file exist"
    source "./.env"
    case "$1" in
    init)
      echo "hmm"
      _init
      ;;
    build)
      projects=$(select_projects "$2")
      for project in $projects; do
        echo "Building $project"
        ./gradlew :$project:bootJar
      done
      ;;
    push)
      projects=$(select_projects "$2")
      for project in $projects; do
        echo "Pushing image $project"
        image="${DOCKER_REGISTRY_PUSH}/services/${project}:latest"
        docker buildx build --platform linux/amd64 -t "${image}" ./$project
        docker push "${image}"
      done
      ;;
    deploy)
      echo "helm must be installed"
      pushd "./deploy"
        helm upgrade -i services . -n services --create-namespace \
          --set defaults.ingress.domain=${TEST_DOMAIN} \
          --set defaults.registry=${DOCKER_REGISTRY_PULL}
      popd
      ;;
      * )
        echo "services [build|push\deploy]"
    esac
  else
    echo "It's the first time you're here so do init first"
    _init
  fi

}
