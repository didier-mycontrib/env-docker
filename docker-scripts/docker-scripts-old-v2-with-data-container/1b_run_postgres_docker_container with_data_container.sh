#!/bin/bash

docker rm postgresdbstore
docker create -v /var/lib/postgresql/data --name postgresdbstore postgres:9 /bin/true


# /script in postgres-container is mapped to 
# /home/poweruser/Bureau/docker-scripts/postgres-scripts of host
# via -v option of "docker run ..." (lauched before "docker exec ...")

export POSTGRES_SCRIPTS_IN_HOST=$(pwd)/postgres-scripts

export POSTGRES_SCRIPTS_IN_CONTAINER=/scripts

docker stop postgres-container
docker rm postgres-container

echo POSTGRES_SCRIPTS_IN_HOST=${POSTGRES_SCRIPTS_IN_HOST}

docker run --name postgres-container \
           -e POSTGRES_PASSWORD=root \
           -h postgres.container.host \
           --volumes-from postgresdbstore \
           -v ${POSTGRES_SCRIPTS_IN_HOST}:${POSTGRES_SCRIPTS_IN_CONTAINER} \
           -d postgres:9

#NB: default username=postgres
docker inspect postgres-container | grep IPAddress
echo suite;read suite
