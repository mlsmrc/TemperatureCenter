#!/bin/bash

MONGO_PORT=27017
MONGO_NAME="mongo_test"

function print()
{
	echo "[INFO] $1"
}
function stopAll()
{
	print "Deleting services on port $MONGO_PORT"
	services=$(docker ps | grep "\->$MONGO_PORT" | cut -d " " -f 1)
	docker rm -f $services 2>/dev/null
	print "Done"
	
	print "Deleting services named $MONGO_NAME"
	services=$(docker ps | grep "$MONGO_NAME" | grep -v "CONTAINER" | cut -d " " -f 1 2>/dev/null)
	docker rm -f $services 2>/dev/null
	print "Done"
}
echo $1
exit 0
[ "$1" == "--stopall" ] && echo "stopall"

## Starting mongo docker for test
print "Starting mongo docker on port $MONGO_PORT"
mongo_id=$(docker run -d -p $27017:$27017 --name $MONGO_NAME -e MONGO_INITDB_ROOT_USERNAME=test -e MONGO_INITDB_ROOT_PASSWORD=test -e MONGO_INITDB_DATABASE=mydatabase mongo)
docker inspect $mongo_id
print "Mongo docker created. ID: $mongo_id"

## Maven Build
./mvnw package
MVN=$?

## Stopping mongo docker for test
print "Stopping mongo docker"
mongo_id_stopped=$(docker stop $mongo_id)
[ $? -eq 0 ] && print "Mongo docker stopped. ID: $mongo_id_stopped" || print "Error stopping mongo docker"

jarfile=$(ls target/api-server*.jar 2>/deb/null)
F=$?
[ $F -eq 0 ] && print "File found: $jarfile" || print "No file found on path target/"

## Exit
RC=$((MVN+F))
print "Exiting $RC"
exit $RC