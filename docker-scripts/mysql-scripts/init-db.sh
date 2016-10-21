#!/bin/bash

# /script in mysql-container is mapped to 
# /home/poweruser/Bureau/docker-scripts/mysql-scripts of host
# via -v option of "docker run ..." (lauched before "docker exec ...")

export MYSQL_SCRIPTS=/scripts
mysql -u root -proot < ${MYSQL_SCRIPTS}/customers-db.sql
mysql -u root -proot < ${MYSQL_SCRIPTS}/purchases-db.sql
mysql -u root -proot < ${MYSQL_SCRIPTS}/orders-db.sql

#mysql -u root -proot < ${MYSQL_SCRIPTS}/create-empty-database.sql
