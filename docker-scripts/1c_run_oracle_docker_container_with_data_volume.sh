#!/bin/bash

docker stop oracle-container
docker rm oracle-container

#docker rm oracledbstore
#docker create -v /u01/app/oracle --name oracledbstore sath89/oracle-xe-11g /bin/true
#docker create -v /u01/app/oracle --name oracledbstore wnameless/oracle-xe-11g /bin/true

docker volume rm oracle-data-volume
docker volume create --name oracle-data-volume

# /script in oracle-container is mapped to 
# /home/poweruser/Bureau/docker-scripts/oracle-scripts of host
# via -v option of "docker run ..." (lauched before "docker exec ...")


export ORACLE_SCRIPTS_IN_HOST=$(pwd)/oracle-scripts

export ORACLE_SCRIPTS_IN_CONTAINER=/scripts

echo ORACLE_SCRIPTS_IN_HOST=${ORACLE_SCRIPTS_IN_HOST}

if [ -e /usr/bin/sudo ]
then
   # nothing on real linux system (as ubuntu)
   export DOCKER_TOOLBOX_IP_PREFIX=
else
   # 192.168.99.100 or ... (with :) in Docker-Toolbox-on-windows
   export DOCKER_TOOLBOX_IP_PREFIX=192.168.99.100:
   echo "DOCKER_TOOLBOX_IP_PREFIX is ${DOCKER_TOOLBOX_IP_PREFIX}"
fi


# -p 22022:22 ou -p 18081:8080 selon image
# image wnameless/oracle-xe-11g ou image sath89/oracle-xe-11g

docker run --name oracle-container \
           -p ${DOCKER_TOOLBOX_IP_PREFIX}18081:8080 -p ${DOCKER_TOOLBOX_IP_PREFIX}1521:1521 \
           -h oracle.container.host \
           -v oracle-data-volume:/u01/app/oracle \
           -v ${ORACLE_SCRIPTS_IN_HOST}:${ORACLE_SCRIPTS_IN_CONTAINER} \
           -d sath89/oracle-xe-11g

# -v ${ORACLE_DATA_STORAGE_DIR}:/??? \ ??? (to many path , oradata , tablespaces , ...)

# sid:XE , username: system or sys, password: oracle , ssh: root@localhost / admin
# jdbc:oracle:thin:@localhost_or_...:49161_or_1521:XE

#docker restart oracle-container
docker inspect oracle-container | grep IPAddress
echo "attendre quelques minutes pour qu'oracle soit parfaitement initialisé "
echo "(premier démarrage lent)"
echo "... , enter pour continuer (après quelques minutes)";read suite
