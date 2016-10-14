#!/bin/bash

# lancement d'un container de type "debian:8" sans nom car execution très courte
# /backup est un alias interne pour $(pwd)
# --volumes-from mysqldbstore permet d'accéder aux volume du "data_container" mysqldbstore
# ou bien-v mysql-data-volume:/var/lib/mysql permet d'associer le chemin interne /var/lib/mysql
# au data volume préparé et nommé "mysql-data-volume"
# la commande tar cvf sera lancée pour créer backup.tar dans $(pwd)

echo "stopping mysql-container";
docker stop mysql-container
echo "pause before saving data , ... , enter to continue";read suite;

#docker run --rm \
#           --volumes-from mysqldbstore \
#           -v $(pwd):/backup debian:8 \
#            tar cvf /backup/mysql-backup.tar /var/lib/mysql

docker run --rm \
           -v mysql-data-volume:/var/lib/mysql \
           -v $(pwd):/backup debian:8 \
            tar cvf /backup/mysql-backup.tar /var/lib/mysql

echo "pause before restarting mysql-container , ... , enter to continue";read suite;
docker start mysql-container

