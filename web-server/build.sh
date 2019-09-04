#!/bin/bash

###########################################
# Variables
###########################################

APPLICATION_VERSION="0.0.1"
LATEST="latest"
DOCKERFILE="Dockerfile"

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
				echo "-r|--raspbian: build for Rasbian/ARM"
				exit 0
				;;
			-r|--raspbian)
				print "Build for Raspbian"
				SUFFIX="-RPi"
				LATEST=${LATEST}${SUFFIX}
				APPLICATION_VERSION=${APPLICATION_VERSION}${SUFFIX}
				DOCKERFILE="DockerfileRPi"
				shift
				;;
		esac
	done
}

###########################################
# Main
###########################################

args $@

## Docker Build
print "Building docker image using ${DOCKERFILE}"
docker build --file ${DOCKERFILE} -t theoriginaltonystark/temperaturecenter_web-server:${LATEST} .
docker build --file ${DOCKERFILE} -t theoriginaltonystark/temperaturecenter_web-server:${APPLICATION_VERSION} .
[ $? -ne 0 ] && err "Error building docker image" && exit 1
print "Docker image built correctly"

## Upload on Docker Hub
if [ $DOCKER_PUSH -eq 1 ]; then
	print "Pushing docker image"
	echo $DOCKER_PWD | docker login -u theoriginaltonystark --password-stdin
	docker push theoriginaltonystark/temperaturecenter_web-server:${LATEST} 
	DL=$?
	docker push theoriginaltonystark/temperaturecenter_web-server:${APPLICATION_VERSION}
	DAP=$?

	[ $DL -ne 0 ] || [ $DAP -ne 0 ] && err "Docker image not pushed" && exit 1
	print "Docker image pushed correctly"
fi

## Exit
exit 0
