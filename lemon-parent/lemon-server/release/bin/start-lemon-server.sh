#!/usr/bin/env bash
baseDir=`cd .. && pwd`
sudo docker-compose stop
sudo docker-compose rm -f
cd ${baseDir}/docker
sudo docker-compose up -d
