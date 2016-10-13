#!/bin/bash

# lancement d'un container de type "debian:8" sans nom car execution très courte
# /backup est un alias interne pour $(pwd)
# --volumes-from mysqldbstore permet d'accéder aux volume du "data_container" mysqldbstore
# la commande tar cvf sera lancée pour charger le contuenu backup.tar dans $(pwd)

echo "stopping oracle-container";
docker stop oracle-container
echo "pause before restauring data , ... , enter to continue";read suite;

#docker run --rm \
#           --volumes-from oracledbstore \
#           -v $(pwd):/backup debian:8 \
#           tar xvf /backup/oracle-backup.tar

docker run --rm \
           -v oracle-data-volume:/u01/app/oracle \
           -v $(pwd):/backup debian:8 \
           tar xvf /backup/oracle-backup.tar

echo "pause before restarting oracle-container , ... , enter to continue";read suite;
docker start oracle-container





