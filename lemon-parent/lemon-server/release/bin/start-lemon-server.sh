#!/usr/bin/env bash
baseDir=`cd .. && pwd`
cd ${baseDir}/docker
sed -i 's/appHome=.*/appHome='${baseDir}'' ${baseDir}/docker/.env
set -e
regxBaseDir=${baseDir////\\/}
echo "sed -i 's/^appHome=.*/appHome='${regxBaseDir}'/' ./.env"
sudo sed -i 's/^appHome=.*/appHome='${regxBaseDir}'/' ./.env
sudo docker-compose stop
sudo docker-compose rm -f
sudo docker images |grep lemon-server |awk '{ print $1}' |xargs sudo docker rmi -f
sudo docker-compose up -d --build
