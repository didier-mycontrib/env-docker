#!/bin/bash

export MVN_REPOSITORY=${HOME}/.m2/repository


export MY_H2_DB_URL=jdbc:h2:~/test

export H2_VERSION=1.4.191
export H2_CLASSPATH=${MVN_REPOSITORY}/com/h2database/h2/${H2_VERSION}/h2-${H2_VERSION}.jar

java -jar  ${H2_CLASSPATH} -user "sa" -url ${MY_H2_DB_URL}

# url=http://localhost:8082

# NB: penser à se déconnecter
#    et options/session actives/arrêt pour éviter des futurs verrous/blocages

echo pause;read suite;