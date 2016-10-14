#!/bin/bash

docker rm mysqldbstore
docker create -v /var/lib/mysql --name mysqldbstore mysql:5.7 /bin/true
#nb: l'option -v (sans :) permet de initialiser/créer (en interne) un "data volume"
#qui sera automatiquement associé à un chemin de type /var/lib/docker/volumes/43f746a1d6...23/_data

# interets du "data_container" : eventuel partage et  portabilite (plus de chemin specifique 
# à l'hote , plus besoin d'eventuel de sudo ou chmod )

# /script in mysql-container is mapped to 
# /home/poweruser/Bureau/docker-scripts/mysql-scripts of host
# or $(pwd)/mysql-scripts of host
# via -v option of "docker run ..." (lauched before "docker exec ...")


export MYSQL_SCRIPTS_IN_HOST=$(pwd)/mysql-scripts


export MYSQL_SCRIPTS_IN_CONTAINER=/scripts

docker stop mysql-container
docker rm mysql-container

echo MYSQL_SCRIPTS_IN_HOST=${MYSQL_SCRIPTS_IN_HOST}

docker run --name mysql-container \
           -e MYSQL_ROOT_PASSWORD=root \
           -h mysql.container.host \
           --volumes-from mysqldbstore \
           -v ${MYSQL_SCRIPTS_IN_HOST}:${MYSQL_SCRIPTS_IN_CONTAINER} \
           -d mysql:5.7

#NB: default username=root
docker inspect mysql-container | grep IPAddress
echo suite;read suite

