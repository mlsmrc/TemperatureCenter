#!/bin/bash

###########################################
# Variables
###########################################

MONGO_PORT=27017
MONGO_NAME="mongo_test"
HOSTNAME="db"

###########################################
# Functions
###########################################

function print()
{
	echo "[INFO] $1"
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
	case $1 in
		--stopall)
			stopAll
			;;
		--help)
			echo "usage: /build.sh [--stopall]"
			echo "--stopall: stop all services on port $MONGO_PORT and all services named $MONGO_NAME."
			echo "           use it for maven build"
			exit 0
			;;
	esac
}
###########################################
# Main
###########################################

args $1

## Starting mongo docker for test
print "Starting mongo docker on port $MONGO_PORT"
mongo_id=$(docker run -d -p $MONGO_PORT:$MONGO_PORT --name $MONGO_NAME -e MONGO_INITDB_ROOT_USERNAME=test -e MONGO_INITDB_ROOT_PASSWORD=test -e MONGO_INITDB_DATABASE=mydatabase mongo)
docker inspect $mongo_id
print "Mongo docker created. ID: $mongo_id"

## Maven Build
./mvnw package
MVN=$?

## Stopping mongo docker for test
print "Stopping mongo docker"
mongo_id_stopped=$(docker stop $mongo_id)
[ $? -eq 0 ] && print "Mongo docker stopped. ID: $mongo_id_stopped" || print "Error stopping mongo docker"

jarfile=$(ls target/api-server*.jar 2>/dev/null)
F=$?
[ $F -eq 0 ] && print "File found: $jarfile" || print "No file found on path target/"

## Exit
RC=$((MVN+F))
print "Exiting $RC"
exit $RC