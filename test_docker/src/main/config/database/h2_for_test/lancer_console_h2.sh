#!/bin/bash

export MVN_REPOSITORY=${HOME}/.m2/repository
export H2_VERSION=1.4.191
export H2_CLASSPATH=${MVN_REPOSITORY}/com/h2database/h2/${H2_VERSION}/h2-${H2_VERSION}.jar

#export MY_H2_DB_URL=jdbc:h2:~/test
export MY_H2_DB_URL1=jdbc:h2:~/customers
export MY_H2_DB_URL2=jdbc:h2:~/orders
export MY_H2_DB_URL3=jdbc:h2:~/purchases


#java -jar  ${H2_CLASSPATH} -user "sa" -url ${MY_H2_DB_URL}
#java -jar  ${H2_CLASSPATH} -user "sa" -url ${MY_H2_DB_URL1}
java -jar  ${H2_CLASSPATH} -user "sa" -url ${MY_H2_DB_URL2}
#java -jar  ${H2_CLASSPATH} -user "sa" -url ${MY_H2_DB_URL3}

# url=http://localhost:8082

# NB: penser à se déconnecter
#    et options/session actives/arrêt pour éviter des futurs verrous/blocages

echo pause;read suite;