#!/bin/bash

###########################################
# Variables
###########################################

MONGO_PORT=27017
MONGO_NAME="mongo_test"
HOSTNAME="db"
APPLICATION_VERSION="0.1.0"
DOCKER_PUSH=0
STOP_ALL=0

###########################################
# Functions
###########################################

function print()
{
	echo "[INFO] $1"
}
function err()
{
	echo "[ERROR] $1"
}
function stopAll()
{
	print "Deleting services on port $MONGO_PORT"
	services=$(docker ps -a | grep "\->$MONGO_PORT" | cut -d " " -f 1)
	docker rm -f $services 2>/dev/null
	print "Done"
	
	print "Deleting services named $MONGO_NAME"
	services=$(docker ps -a | grep "$MONGO_NAME" | grep -v "CONTAINER" | cut -d " " -f 1 2>/dev/null)
	docker rm -f $services 2>/dev/null
	print "Done"
}
function args()
{
	while [ $# -gt 0 ]; do
		key=$1
		case $1 in
			-f|--force)
				STOP_ALL=1
				print "STOP all services on port $MONGO_PORT and all services named $MONGO_NAME activated"
				shift
				;;
				
			-p|--push)
				shift
				if [ $# -gt 1 ]; then
					DOCKER_PWD=$1
					[[ "$DOCKER_PWD" == "--"* ]] && err "Missing DOCKER_PWD" && exit 1
				elif [ $# -eq 0 ]; then
					err "Missing DOCKER_PWD" && exit 1
				fi
				print "PUSH to docker hub activated"
				DOCKER_PUSH=1
				shift
				;;
				
			-h|--help)
				echo "usage: /build.sh [-f|--force] [-p|--push DOCKER_PWD]"
				echo "-f|--force: stop all services on port $MONGO_PORT and all services named $MONGO_NAME."
				echo "            use it for maven build"
				echo "-p|--push: push images to Docker HUB"
				exit 0
				;;
		esac
	done
}
###########################################
# Main
###########################################

args $@

## STOP all services on port $MONGO_PORT and all services named $MONGO_NAME activated
[ $STOP_ALL -eq 1 ] && stopAll

## Starting mongo docker for test
print "Starting mongo docker on port $MONGO_PORT"
mongo_id=$(docker run -d -p $MONGO_PORT:$MONGO_PORT --name $MONGO_NAME -e MONGO_INITDB_ROOT_USERNAME=test -e MONGO_INITDB_ROOT_PASSWORD=test -e MONGO_INITDB_DATABASE=mydatabase mongo)
docker inspect $mongo_id
print "Mongo docker created. ID: $mongo_id"

## Maven Build
./mvnw package
[ $? -ne 0 ] && print "Error testing api-server" && exit 1
print "api-server tested correctly"

## Stopping mongo docker for test
print "Stopping mongo docker"
mongo_id_stopped=$(docker stop $mongo_id)
[ $? -ne 0 ] && print "Error stopping mongo docker" && exit 1
print "Mongo docker stopped. ID: $mongo_id_stopped"

jarfile=$(ls target/api-server*.jar 2>/dev/null)
[ $? -ne 0 ] && print "No file found on path target/" && exit 1
print "File found: $jarfile"

## Docker Build
docker build -t theoriginaltonystark/temperaturecenter_api-server:latest .
docker build -t theoriginaltonystark/temperaturecenter_api-server:${APPLICATION_VERSION} .
[ $? -ne 0 ] && print "Error building docker image" && exit 1
print "Docker image built correctly"

## Upload on Docker Hub
if [ $DOCKER_PUSH -eq 1 ]; then
	echo $DOCKER_PWD | docker login --username=theoriginaltonystark --password-stdin
	docker push theoriginaltonystark/temperaturecenter_api-server:latest
fi

## Exit
exit 0