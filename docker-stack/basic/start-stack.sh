docker network create --driver bridge mynetwork

#NB: version basique (sans clustering) devise.db.service ici extremement simplifié au sens "unique instance" (devise.db.host)
#NB: version basique (sans clustering) devise.api.service ici extremement simplifié au sens "unique instance" (devise.api.host)

docker volume create --name mysql-data-volume
docker run -p 3306:3306 -d --name devise-db-container \
            -v mysql-data-volume:/var/lib/mysql \
            --network mynetwork --network-alias=devise.db.service xyz/devise-db

docker run -p 8282:8282 -d --name devise-api-container \
           -h devise.api.host  \
           --network mynetwork --network-alias=devise.api.service xyz/devise-api

docker run -p 80:80 -d --network mynetwork --name devise-ngapp-container xyz/devise-ngapp
