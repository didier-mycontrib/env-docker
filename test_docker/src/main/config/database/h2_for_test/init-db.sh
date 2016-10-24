#!/bin/bash

export MVN_REPOSITORY=${HOME}/.m2/repository
export H2_VERSION=1.4.191
export H2_CLASSPATH=${MVN_REPOSITORY}/com/h2database/h2/${H2_VERSION}/h2-${H2_VERSION}.jar

#export MY_H2_DB_URL=jdbc:h2:~/test

export MY_H2_DB_URL1=jdbc:h2:~/customers
export MY_H2_DB_SCRIPT1=customers-db.sql

export MY_H2_DB_URL2=jdbc:h2:~/orders
export MY_H2_DB_SCRIPT2=orders-db.sql

export MY_H2_DB_URL3=jdbc:h2:~/purchases
export MY_H2_DB_SCRIPT3=purchases-db.sql



java -classpath ${H2_CLASSPATH}  org.h2.tools.RunScript -user sa -showResults \
     -url ${MY_H2_DB_URL1}  -script ${MY_H2_DB_SCRIPT1}
     
java -classpath ${H2_CLASSPATH}  org.h2.tools.RunScript -user sa -showResults \
     -url ${MY_H2_DB_URL2}  -script ${MY_H2_DB_SCRIPT2}
     
java -classpath ${H2_CLASSPATH}  org.h2.tools.RunScript -user sa -showResults \
     -url ${MY_H2_DB_URL3}  -script ${MY_H2_DB_SCRIPT3}
     
    
     
echo pause;read suite;     
