#!/bin/bash

docker start mysql-container
docker start postgres-container
#docker start oracle-container

echo "pause , ... , enter to continue";read suite
