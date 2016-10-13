#!/bin/bash

docker stop mysql-container
docker rm mysql-container

docker volume rm mysql-data-volume
docker volume create --name mysql-data-volume
docker volume inspect mysql-data-volume

# interets du "data_volume nommé" : portabilite (plus de chemin specifique 
# à l'hote , plus besoin d'eventuel de sudo ou chmod )

# /script in mysql-container is mapped to 
# /home/poweruser/Bureau/docker-scripts/mysql-scripts of host
# or $(pwd)/mysql-scripts of host
# via -v option of "docker run ..." (lauched before "docker exec ...")

export MYSQL_SCRIPTS_IN_HOST=$(pwd)/mysql-scripts

export MYSQL_SCRIPTS_IN_CONTAINER=/scripts



echo MYSQL_SCRIPTS_IN_HOST=${MYSQL_SCRIPTS_IN_HOST}

docker run --name mysql-container \
           -e MYSQL_ROOT_PASSWORD=root \
           -h mysql.container.host \
           -v mysql-data-volume:/var/lib/mysql \
           -v ${MYSQL_SCRIPTS_IN_HOST}:${MYSQL_SCRIPTS_IN_CONTAINER} \
           -d mysql:5.7

#NB: default username=root
docker inspect mysql-container | grep IPAddress
echo suite_et_fin;read suite

