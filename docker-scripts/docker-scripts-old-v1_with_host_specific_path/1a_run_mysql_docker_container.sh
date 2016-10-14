#!/bin/bash

#linux path:
export MYSQL_DATA_STORAGE_DIR=/storage/docker/mysql-datadir
#windows docker_toolbox_path:
#export MYSQL_DATA_STORAGE_DIR=~/dock/storage/mysql-datadir

sudo mkdir -p ${MYSQL_DATA_STORAGE_DIR}
#may be not sudo for windows / docker-toolbox


# /script in mysql-container is mapped to 
# /home/poweruser/Bureau/docker-scripts/mysql-scripts of host
# via -v option of "docker run ..." (lauched before "docker exec ...")

#linux path:
export MYSQL_SCRIPTS_IN_HOST=/home/poweruser/Bureau/docker-scripts/mysql-scripts
#windows docker_toolbox_path:
#export MYSQL_SCRIPTS_IN_HOST=~/dock/docker-scripts/mysql-scripts

export MYSQL_SCRIPTS_IN_CONTAINER=/scripts

docker stop mysql-container
docker rm mysql-container

docker run --name mysql-container \
           -e MYSQL_ROOT_PASSWORD=root \
           -h mysql.container.host \
           -v ${MYSQL_DATA_STORAGE_DIR}:/var/lib/mysql \
           -v ${MYSQL_SCRIPTS_IN_HOST}:${MYSQL_SCRIPTS_IN_CONTAINER} \
           -d mysql:5.7

#NB: default username=root

echo suite;read suite

