#!/bin/bash

#linux path:
export POSTGRES_DATA_STORAGE_DIR=/storage/docker/postgres-datadir
#windows docker_toolbox_path:
#export POSTGRES_DATA_STORAGE_DIR=~/dock/storage/postgres-datadir

sudo mkdir -p ${POSTGRES_DATA_STORAGE_DIR}
#may be not sudo for windows / docker-toolbox


# /script in postgres-container is mapped to 
# /home/poweruser/Bureau/docker-scripts/postgres-scripts of host
# via -v option of "docker run ..." (lauched before "docker exec ...")

#linux path:
export POSTGRES_SCRIPTS_IN_HOST=/home/poweruser/Bureau/docker-scripts/postgres-scripts
#windows docker_toolbox_path:
#export POSTGRES_SCRIPTS_IN_HOST=~/dock/docker-scripts/postgres-scripts


export POSTGRES_SCRIPTS_IN_CONTAINER=/scripts

docker stop postgres-container
docker rm postgres-container

docker run --name postgres-container \
           -e POSTGRES_PASSWORD=root \
           -h postgres.container.host \
           -v ${POSTGRES_DATA_STORAGE_DIR}:/var/lib/postgresql \
           -v ${POSTGRES_SCRIPTS_IN_HOST}:${POSTGRES_SCRIPTS_IN_CONTAINER} \
           -d postgres:9

#NB: default username=postgres

echo suite;read suite
