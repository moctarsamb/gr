#!/usr/bin/env bash

docker-compose -f ./src/main/docker/jhipster-registry.yml down --remove-orphans

docker-compose -f ./src/main/docker/keycloak.yml down --remove-orphans

docker-compose -f ./src/main/docker/elasticsearch.yml down --remove-orphans
