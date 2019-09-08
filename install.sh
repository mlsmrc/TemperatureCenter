#!/bin/bash

##########################
# TC Constants
##########################

TC_FOLDER="/temperaturecenter"
TC_FOLDER_BIN=${TC_FOLDER}"/bin"
TC_LOG=${TC_FOLDER}"/log/docker-compose.log"

##########################
# Constants
##########################

WEB_SERVER_PORT="80"
CHROME_STARTUP="@/usr/bin/chromium-browser --kiosk --disable-infobars --disable-restore-session-state http://127.0.0.1:${WEB_SERVER_PORT}"
MOUSE_HIDDEN="@unclutter -idle 0"
STANDBY_AVOIDING="@xset s noblank\n@xset s off\n@xset -dpms"
LXDE_AUTOSTART="/home/pi/.config/lxsession/LXDE-pi/autostart"
LCD35_URL="https://raw.githubusercontent.com/goodtft/LCD-show/master/LCD35-show"
LCD35="${TC_FOLDER_BIN}/lcd35"



##########################
# Functions
##########################

# print message
function print()
{
	echo "[INFO] $1"
}

# print error message
function err()
{
	echo "[ERROR] $1"
}

function Done() { print "Done!" }

# Create folders
function createFolders()
{
	mkdir -p $TC_FOLDER $TC_FOLDER_BIN
	sudo chown pi:pi -r $TC_FOLDER
}

# Install Docker
function installDocker()
{
	p=$(which docker)
	
	if [ $? -ne 0 ]; then
		print "Install Docker"
		curl -fsSL https://get.docker.com -o get-docker.sh
		sh get-docker.sh
		sudo usermod -aG docker pi
		sudo chmod 777 /var/run/docker.sock
		rm -rf get-docker.sh
		Done
	else
		print "Docker already installed in path $p"
	fi
}

# Install Docker Compose
function installDockerCompose()
{
	p=$(which docker-compose)
	if [ $? -ne 0 ]; then
		print "Install Docker Compose"
		curl https://bootstrap.pypa.io/get-pip.py -o get-pip.py && sudo python3 get-pip.py
		sudo pip3 install docker-compose
		rm -rf get-pip.py
		Done
	else
		print "Docker Compose already installed in path $p"
	fi
}

# Get LCD Drivers
function getLcdDriver()
{
	p=$(ls $LCD35)
	if [ $? -ne 0 ]; then
		print "Get LCD35 Drivers"
		sudo apt-mark hold raspberrypi-bootloader
		sudo apt-get -y upgrade
		curl $LCD35_URL > $LCD35
		Done
	else
		print "LCD35 driver found in path $p"
	fi
}
function enableLcd()
{
	$LCD35
}

# Make the mouse pointer hidden
function hideMouse()
{
	grep -q "$MOUSE_HIDDEN" "$LXDE_AUTOSTART"
	if [ $? -ne 0 ]; then
		print "Make mouse pointer hidden"
		sudo apt-get install unclutter
		echo $MOUSE_HIDDEN >> $LXDE_AUTOSTART
		Done
	else
		print "Mouse pointer already set as hidden"
	fi
}

# Open Chrome at startup time, pointing to 127.0.0.1:80
function chromeStartup()
{
	grep -q "$CHROME_STARTUP" "$LXDE_AUTOSTART"
	if [ $? -ne 0 ]; then
		print "Add Chrome at 127.0.0.1 during startup"
		echo $CHROME_STARTUP >> $LXDE_AUTOSTART
		Done
	else
		print "Chrome already add during startup"
	fi
}

# Avoid screen standby
function disableStandby()
{
	grep -q "$STANDBY_AVOIDING" "$LXDE_AUTOSTART"
	if [ $? -ne 0 ]; then
		print "Disable screen standby"
		echo -e $STANDBY_AVOIDING >>  $LXDE_AUTOSTART
		Done
	else
		print "Screen standby already disabled"
	fi
}

##########################
# Main
##########################

# Folders
createFolders

# Docker
installDocker
installDockerCompose

# Screen
getLcdDriver
hideMouse
chromeStartup
disableStandby