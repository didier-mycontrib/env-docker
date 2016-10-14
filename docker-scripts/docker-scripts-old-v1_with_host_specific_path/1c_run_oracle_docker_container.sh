#!/bin/bash

#linux path:
#export ORACLE_DATA_STORAGE_DIR=/storage/docker/oracle-datadir
#windows docker_toolbox_path:
#export ORACLE_DATA_STORAGE_DIR=~/dock/storage/oracle-datadir

#sudo mkdir -p ${ORACLE_DATA_STORAGE_DIR}
#may be not sudo for windows / docker-toolbox


# /script in oracle-container is mapped to 
# /home/poweruser/Bureau/docker-scripts/oracle-scripts of host
# via -v option of "docker run ..." (lauched before "docker exec ...")

#linux path:
export ORACLE_SCRIPTS_IN_HOST=/home/poweruser/Bureau/docker-scripts/oracle-scripts
#windows docker_toolbox_path:
#export ORACLE_SCRIPTS_IN_HOST=~/dock/docker-scripts/oracle-scripts

export ORACLE_SCRIPTS_IN_CONTAINER=/scripts

docker stop oracle-container
docker rm oracle-container

docker run --name oracle-container \
           -e ORACLE_ALLOW_REMOTE=true \
           -p 22022:22 -p 1521:1521 \
           -h oracle.container.host \
           -v ${ORACLE_SCRIPTS_IN_HOST}:${ORACLE_SCRIPTS_IN_CONTAINER} \
           -d wnameless/oracle-xe-11g

# -v ${ORACLE_DATA_STORAGE_DIR}:/??? \ ??? (to many path , oradata , tablespaces , ...)

# sid:XE , username: system or sys, password: oracle , ssh: root@localhost / admin
# jdbc:oracle:thin:@localhost_or_...:49161_or_1521:XE

echo suite;read suite
