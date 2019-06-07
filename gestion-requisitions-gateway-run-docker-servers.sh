#!/usr/bin/env bash

echo "========== Stopping running containers =========="
./gestion-requisitions-gateway-stop-docker-servers.sh

echo "========== Starting containers =========="
docker-compose -f ./src/main/docker/jhipster-registry.yml up -d

docker-compose -f ./src/main/docker/keycloak.yml up -d

docker-compose -f ./src/main/docker/elasticsearch.yml up -d
