#!/bin/bash

# /script in oracle-container is mapped to 
# /home/poweruser/Bureau/docker-scripts/oracle-scripts of host
# via -v option of "docker run ..." (lauched before "docker exec ...")

export ORACLE_SCRIPTS=/scripts

export SQLPLUS=/u01/app/oracle/product/11.2.0/xe/bin/sqlplus
#export SQLPLUS=sqlplus


export ORACLE_HOME=/u01/app/oracle/product/11.2.0/xe
export ORACLE_SID=XE

# sqlplus username/password
${SQLPLUS}  system/oracle < ${ORACLE_SCRIPTS}/mydb.sql
