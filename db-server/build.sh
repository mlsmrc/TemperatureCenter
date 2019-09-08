#!/bin/bash

###########################################
# Variables
###########################################

APPLICATION_VERSION="0.1.0"
DOCKER_PUSH=0

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

function args()
{
	while [ $# -gt 0 ]; do
		key=$1
		case $1 in
				
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
				echo "usage: /build.sh [-p|--push DOCKER_PWD]"
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

## Docker Build
print "Building docker image"
docker build -t theoriginaltonystark/temperaturecenter_db-server:latest .
docker build -t theoriginaltonystark/temperaturecenter_db-server:${APPLICATION_VERSION} .
[ $? -ne 0 ] && err "Error building docker image" && exit 1
print "Docker image built correctly"

## Upload on Docker Hub
if [ $DOCKER_PUSH -eq 1 ]; then
	print "Pushing docker image"
	echo $DOCKER_PWD | docker login --username=theoriginaltonystark --password-stdin
	docker push theoriginaltonystark/temperaturecenter_db-server:latest
	print "Docker image pushed"
fi

## Exit
exit 0