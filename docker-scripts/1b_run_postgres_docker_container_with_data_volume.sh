#!/bin/bash

docker stop postgres-container
docker rm postgres-container

docker volume rm postgres-data-volume
docker volume create --name postgres-data-volume
docker volume inspect postgres-data-volume

# interets du "data_volume nommé" : portabilite (plus de chemin specifique 
# à l'hote , plus besoin d'eventuel de sudo ou chmod )


# /script in postgres-container is mapped to 
# /home/poweruser/Bureau/docker-scripts/postgres-scripts of host
# via -v option of "docker run ..." (lauched before "docker exec ...")

export POSTGRES_SCRIPTS_IN_HOST=$(pwd)/postgres-scripts

export POSTGRES_SCRIPTS_IN_CONTAINER=/scripts

echo POSTGRES_SCRIPTS_IN_HOST=${POSTGRES_SCRIPTS_IN_HOST}

if [ -e /usr/bin/sudo ]
then
   # nothing on real linux system (as ubuntu)
   export DOCKER_TOOLBOX_IP_PREFIX=
else
   # 192.168.99.100 or ... (with :) in Docker-Toolbox-on-windows
   export DOCKER_TOOLBOX_IP_PREFIX=192.168.99.100:
   echo "DOCKER_TOOLBOX_IP_PREFIX is ${DOCKER_TOOLBOX_IP_PREFIX}"
fi

docker run --name postgres-container \
           -e POSTGRES_PASSWORD=root \
           -h postgres.container.host \
	   -p ${DOCKER_TOOLBOX_IP_PREFIX}5432:5432 \
           -v postgres-data-volume:/var/lib/postgresql/data \
           -v ${POSTGRES_SCRIPTS_IN_HOST}:${POSTGRES_SCRIPTS_IN_CONTAINER} \
           -d postgres:9

#NB: default username=postgres
docker inspect postgres-container | grep IPAddress
echo suite_et_fin;read suite
