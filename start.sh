#!/bin/bash

do_local() {
  PROFILE=default

  for APP in discovery-server orcid-service org-service edu-service
  do
    java -jar -Dspring.profiles.active=${PROFILE} ${APP}/target/${APP}.jar &
  done
}

do_docker() {
  #docker compose --file docker-compose.yml up --detach
  docker compose up --scale orcid-service=2
}

. ./do.sh Start $@