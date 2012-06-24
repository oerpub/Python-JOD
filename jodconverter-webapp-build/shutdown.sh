#!/bin/bash
# Author: Gbenga Badipe
#
# Date: 4.24.12
#
# Purpose: Script for shutting down an open JOD process. Will print out error message if JOD is not running
#
# Requirements: None
#
# Effects: Shutsdown JOD

# Checks that the script is being called as the super user
if [ $EUID -ne 0 ]
	then
	echo "This script must be run using sudo";
	exit 0;
fi
DIR="$(pwd)"
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
