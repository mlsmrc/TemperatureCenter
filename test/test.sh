#!/bin/bash

SLEEP_SEC=5

function postRandomT()
{
	location=$1
	t=$(($RANDOM%30)).$(($RANDOM%9))
	echo "$(date) ${location} -> ${t}°C"
	curl -X POST --header "Content-Type:application/json" -d $(($RANDOM%30)).$(($RANDOM%9)) http://127.0.0.1:8080/api/temperature/${location}
}
function testApiServer()
{
	nc -w 2 127.0.0.1 8080
	return $?
}

function postRandom()
{
	while true; do
		postRandomT "cameretta"
		postRandomT "cameradaletto"
		postRandomT "esterna"
		sleep ${SLEEP_SEC}
	done
}


testApiServer
[ $? -ne 0 ] && echo "127.0.0.1 8080 not reachable" && exit 1

postRandom