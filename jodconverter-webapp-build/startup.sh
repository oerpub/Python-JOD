#!/bin/bash
# Author: Gbenga Badipe
#
# Date: 4.24.12
#
# Purpose: Script for starting JOD process. 
#
# Requirements: Script should be run as super user using 'sudo'
#
# Effects: Shuts down JOD in case(even if it is not running) before starting up. 
#          This will avoid duplicate named pipes which ofter causes Linux crashes

# Checks that the script is being called as the super user
if [ $EUID -ne 0 ]
	then
	echo "This script must be run using sudo";
	exit 0;
fi
# Gets working directory
DIR="$(pwd)"
#Shuts down JOD
# Makes sure that there are no other pipes open already 
echo ""
echo "Shutting down server..."
echo ""
cd "$DIR"/jodconverter-tomcat-2.2.2/bin/
./shutdown.sh
cd -
echo ""
echo "Checking for open office processes.."
result="$(netstat | grep office[0-9])"
echo "Waiting for office processes to close..."
while [ -n "$result" ]
	do
	result="$(netstat | grep office[0-9])"
done

#Starts up server
echo "Starting up server..."
cd "$DIR"/jodconverter-tomcat-2.2.2/bin/
./startup.sh
cd -
# Checks to see if openoffice pipes are open for conversions
instances="$(netstat | grep office[0-9])"
if [ -n "$instances" ];
	then
	echo "Error Installation failed"
fi
