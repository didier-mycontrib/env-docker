#!/bin/bash

# /script in oracle-container is mapped to 
# /home/poweruser/Bureau/docker-scripts/oracle-scripts of host
# via -v option of "docker run ..." (lauched before "docker exec ...")

export ORACLE_SCRIPTS=/scripts


docker exec oracle-container bash -c ${ORACLE_SCRIPTS}/init-db.sh

echo pause; read pause;

#docker exec  -it oracle-container bash 

