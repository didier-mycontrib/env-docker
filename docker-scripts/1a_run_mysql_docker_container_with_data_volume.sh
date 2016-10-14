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

if [ -e /usr/bin/sudo ]
then
   # nothing on real linux system (as ubuntu)
   export DOCKER_TOOLBOX_IP_PREFIX=
else
   # 192.168.99.100 or ... (with :) in Docker-Toolbox-on-windows
   export DOCKER_TOOLBOX_IP_PREFIX=192.168.99.100:
   echo "DOCKER_TOOLBOX_IP_PREFIX is ${DOCKER_TOOLBOX_IP_PREFIX}"
fi


docker run --name mysql-container \
           -e MYSQL_ROOT_PASSWORD=root \
           -h mysql.container.host \
           -p ${DOCKER_TOOLBOX_IP_PREFIX}3306:3306 \
           -v mysql-data-volume:/var/lib/mysql \
           -v ${MYSQL_SCRIPTS_IN_HOST}:${MYSQL_SCRIPTS_IN_CONTAINER} \
           -d mysql:5.7

#NB: default username=root
docker inspect mysql-container | grep IPAddress
echo suite_et_fin;read suite

