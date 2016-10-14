#!/bin/bash


#export DOCKER_IMAGE_NAME=mysql:5.7
#export DOCKER_ARCHIVE=mysql_5_7.tar
#export DOCKER_IMAGE_NAME=debian:8
#export DOCKER_ARCHIVE=debian_8.tar
#export DOCKER_IMAGE_NAME=java:8
#export DOCKER_ARCHIVE=java_8.tar
#export DOCKER_IMAGE_NAME=postgres:9
#export DOCKER_ARCHIVE=postgres_9.tar
#export DOCKER_IMAGE_NAME=wnameless/oracle-xe-11g
export DOCKER_IMAGE_NAME=sath89/oracle-xe-11g
export DOCKER_ARCHIVE=oracle-xe-11g.tar

export DOCKER_IMPORT_EXPORT_DIR=/media/sf_ext/docker-images

echo "saving ${DOCKER_IMAGE_NAME} into ${DOCKER_IMPORT_EXPORT_DIR}/${DOCKER_ARCHIVE}"

docker save -o ${DOCKER_IMPORT_EXPORT_DIR}/${DOCKER_ARCHIVE} ${DOCKER_IMAGE_NAME}
# commande symetrique:
# docker load -i ${DOCKER_IMPORT_EXPORT_DIR}/${DOCKER_ARCHIVE}

echo fin;read suite;

