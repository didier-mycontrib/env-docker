#!/bin/bash

docker stop td
docker rm td

#docker run --name td --link mysql-container:mysqlHost tp/test-docker:1
#docker run --name td --link postgres-container:postgresHost tp/test-docker:1
docker run --name td --link oracle-container:oracleHost tp/test-docker:1

echo suite; read suite;


