#!/bin/sh

SCRIPT_DIR=$(dirname "$0")

select_projects() {
  if [[ "${1}" ]]; then
    echo $1
  else
    echo $(gum choose --no-limit  products customers categories main)
  fi
}

services() {
  source "${SCRIPT_DIR}/.env"
  case "$1" in
  init)
    echo "select JAVA_HOME"
    JAVA_HOME=$(gum file ${JAVA_HOME}/.. --directory)
    echo "Set Docker Registry"
    DOCKER_REGISTRY=$(gum input --placeholder "JAVA_HOME" --value="${DOCKER_REGISTRY:-"localhost:5000"}")
    echo "JAVA_HOME=${JAVA_HOME}" > "${SCRIPT_DIR}/.env"
    echo "DOCKER_REGISTRY=${DOCKER_REGISTRY}" >> "${SCRIPT_DIR}/.env"
    ;;
  build)
    projects=($(select_projects "$2"))
    for project in $projects; do
      echo "Building $project"
      ./gradlew :$project:bootJar
    done
    ;;
  push)
    projects=($(select_projects "$2"))
    for project in $projects; do
      echo "Pushing image $project"
      image="${DOCKER_REGISTRY}/services/${project}:latest"
      docker buildx build --platform linux/amd64 -t "${image}" ./$project
      docker push "${image}"
    done
    ;;
  deploy)
    echo "helm must be installed"
    pushd "${SCRIPT_DIR}/deploy"
      helm upgrade -i services . -n services --create-namespace
    popd
    ;;
    * )
      echo "services [build|push\deploy]"
  esac

}
