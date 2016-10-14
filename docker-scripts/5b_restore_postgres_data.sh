#!/bin/bash

# lancement d'un container de type "debian:8" sans nom car execution très courte
# /backup est un alias interne pour $(pwd)
# --volumes-from postgresdbstore permet d'accéder aux volume du "data_container" postgresdbstore
# ou bien -v postgres-data-volume:/var/lib/postgresql/data permet d'associer le chemin interne 
# /var/lib/postgresql/data au data volume préparé et nommé "postgres-data-volume"
# la commande tar cvf sera lancée pour charger le contuenu backup.tar dans $(pwd)

echo "stopping postgres-container";
docker stop postgres-container
echo "pause before restauring data , ... , enter to continue";read suite;

#docker run --rm \
#           --volumes-from postgresdbstore \
#           -v $(pwd):/backup debian:8 \
#           tar xvf /backup/postgres-backup.tar

docker run --rm \
           -v postgres-data-volume:/var/lib/postgresql/data \
           -v $(pwd):/backup debian:8 \
           tar xvf /backup/postgres-backup.tar

echo "pause before restarting postgres-container , ... , enter to continue";read suite;
docker start postgres-container





