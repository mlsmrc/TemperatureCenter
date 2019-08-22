#!/bin/bash

MONGO_PORT=27017
function print()
{
	echo "[INFO] $1"
}

print "Starting mongo docker on port $MONGO_PORT"
mongo_id=$(docker run -d -p 27017:27017 -e MONGO_INITDB_ROOT_USERNAME=test -e MONGO_INITDB_ROOT_PASSWORD=test -e MONGO_INITDB_DATABASE=mydatabase mongo)
print "Mongo docker created. ID: $mongo_id"
./mvnw test
RC=$?

print "Stopping mongo docker"
mongo_id_stopped=$(docker stop $mongo_id)
[ $? -eq 0 ] && print "Mongo docker stopped. ID: $mongo_id_stopped" || print "Error stopping mongo docker"

exit $RC