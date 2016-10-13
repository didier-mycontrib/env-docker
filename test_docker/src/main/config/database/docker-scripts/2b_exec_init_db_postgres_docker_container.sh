#!/bin/bash

# /script in postgres-container is mapped to 
# /home/poweruser/Bureau/docker-scripts/postgres-scripts of host
# via -v option of "docker run ..." (lauched before "docker exec ...")

export POSTGRES_SCRIPTS=/scripts


docker exec postgres-container bash -c ${POSTGRES_SCRIPTS}/init-db.sh

echo pause; read pause;

#docker exec  -it postgres-container bash 



