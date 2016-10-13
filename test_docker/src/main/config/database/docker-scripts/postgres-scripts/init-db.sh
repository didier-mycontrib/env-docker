#!/bin/bash

# /script in postgres-container is mapped to 
# /home/poweruser/Bureau/docker-scripts/postgres-scripts of host
# via -v option of "docker run ..." (lauched before "docker exec ...")

export POSTGRES_SCRIPTS=/scripts
psql -U postgres -f ${POSTGRES_SCRIPTS}/mydb.sql
