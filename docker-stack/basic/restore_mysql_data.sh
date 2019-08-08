#!/bin/bash

# lancement d'un container de type "debian:9" sans nom car execution très courte
# /backup est un alias interne pour $(pwd)
# --volumes-from devise-db-container permet d'accéder aux volume du "data_container" devise-db-container
# ou bien-v mysql-data-volume:/var/lib/mysql permet d'associer le chemin interne /var/lib/mysql
# au data volume préparé et nommé "mysql-data-volume"
#la commande tar cvf sera lancée pour charger le contenu de backup.tar dans $(pwd)

echo "stopping mysql-container";
docker container stop devise-db-container
echo "pause before restoring data , ... , enter to continue";read suite;

#docker run --rm \
#           --volumes-from devise-db-container \
#           -v $(pwd):/backup debian:9 \
#           tar xvf /backup/mysql-backup.tar

docker run --rm \
           -v mysql-data-volume:/var/lib/mysql \
           -v $(pwd):/backup debian:9 \
           tar xvf /backup/mysql-backup.tar

echo "pause before restarting mysql-container , ... , enter to continue";read suite;
docker container start devise-db-container