#!/bin/bash

###########################################
# Variables
###########################################

USER="pi"

###########################################
# Utils
###########################################

function print() { echo "[INFO] $1" }
function err() { echo "[ERROR] $1" }
function cmd() { print "$1"; $1; }
function ok() { print "Done!" }

###########################################
# Prereq
###########################################

function clean()
{
	docker image rm -f hello-world &>/dev/null
}
function installComponents()
{
	sudo apt-get -y update && apt-get -y purge
	sudo apt-get -y install apt-transport-https ca-certificates curl gnupg-agent software-properties-common
	sudo apt-get -y install python python-pip libffi-dev python-backports.ssl-match-hostname
}

function installDocker()
{
	print "Docker install"
	curl -sSL https://get.docker.com | bash
	[ $? -ne 0 ] && clean && err "Docker installation script not downloaded" && exit 1
	ok
	
	print "Provide Docker privileged to a non-root user"
	sudo usermod -aG docker pi
	[ $? -ne 0 ] && clean && err "Docker privileges not provided to a non-root user" && exit 1
	ok
	
	print "Check installation"
	docker run hello-world &>/dev/null | grep -q "Hello from Docker"
	[ $? -ne 0 ] && clean && err "Docker not installed correctly" && exit 1
	ok
	
	print "Change docker.sock permission"
	sudo chown $USER:docker /var/run/docker.sock
	ok
}
function startDockerAtBoot()
{
	print "Start Docker at boot"
	sudo systemctl enable docker
	sudo systemctl start docker
	ok
}

function installDockerCompose()
{
	print "Install Docker compose"
	sudo pip install docker-compose
	docker-compose --version
	[ $? -ne 0 ] && err "Docker-compose not installed correctly" && exit 1
	ok
}

function installPrereq
{
	installComponents
	installDocker
	startDockerAtBoot
	installDockerCompose
	clean
}

###########################################
# Temperature Center Functions
###########################################

function installTemperatureCenter
{
	print "Download Temperature Center - docker-compose.yml"
	curl -L https://raw.githubusercontent.com/mlsmrc/TemperatureCenter/master/docker/docker-compose.yml -o docker-compose.yml
	docker-compose start
}

###########################################
# Main Installation
###########################################

installPrereq
installTemperatureCenter