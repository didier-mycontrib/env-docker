#!/bin/bash

# /script in mysql-container is mapped to 
# /home/poweruser/Bureau/docker-scripts/mysql-scripts of host
# via -v option of "docker run ..." (lauched before "docker exec ...")

export MYSQL_SCRIPTS=/scripts
mysql -u root -proot < ${MYSQL_SCRIPTS}/mydb.sql
