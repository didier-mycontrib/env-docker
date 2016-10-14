#!/bin/bash

# lancement d'un container de type "debian:8" sans nom car execution très courte
# /backup est un alias interne pour $(pwd)
# --volumes-from mysqldbstore permet d'accéder aux volume du "data_container" mysqldbstore
# la commande tar cvf sera lancée pour charger le contuenu backup.tar dans $(pwd)

echo "stopping mysql-container";
docker stop mysql-container
echo "pause before restauring data , ... , enter to continue";read suite;

docker run --rm \
           --volumes-from mysqldbstore \
           -v $(pwd):/backup debian:8 \
           tar xvf /backup/mysql-backup.tar

echo "pause before restarting mysql-container, ... , enter to continue";read suite;
docker start mysql-container




