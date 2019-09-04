#!/bin/bash

###########################################
# Variables
###########################################

DOCKERFILE="docker/docker-compose.yml"
CMD=
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

function getTCContainers()
{
	grep container_name ${DOCKERFILE} | tr -d " " | cut -d ":" -f 2
}
function stopAll()
{
	docker rm -f $(getTCContainers) 2>/dev/null
	return $?
}
function run()
{
	[ ! -z "$1" ] && docker-compose --file ${DOCKERFILE} $CMD
}
function args()
{
	while [ $# -gt 0 ]; do
		key=$1
		case $1 in
			-f|--force)
				STOP_ALL=1
				print "STOP all containers"
				shift
				;;
				
			-h|--help)
				echo "usage: /tc.sh [-f|--force] [-r|--raspbian] -c|--command [start|stop|up|down]"
				echo "-f|--force: stop all Temperature Center containers"
				echo "-r|--raspbian: run commands for Raspbian"
				exit 0
				;;
			-r|--raspbian)
				print "Build for Raspbian"
				SUFFIX="-RPi"
				DOCKERFILE="docker/docker-compose-rpi.yml"
				shift
				;;
			-c|--command)
				shift
				CMD=$1
				if [[ "$CMD" != "start" ]] && [[ "$CMD" != "stop" ]] && [[ "$CMD" != "up" ]] && [[ "$CMD" != "down" ]]; then
					err "Invalid command: $CMD"
					args "--help"
				fi
				shift
				;;
		esac
	done
	[ -z "$CMD" ] && [ $STOP_ALL -eq 0 ] && err "Command missing - use -c|--command" && args "--help"

}

###########################################
# Main
###########################################

args $@
[[ $STOP_ALL -eq 1 ]] && stopAll
run $CMD
