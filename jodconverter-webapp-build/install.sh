#!/bin/bash
# Author: Gbenga Badipe
#
# Date: 4.18.12
#
# Purpose: Installs JOD converter on local system.
#
# Requirements: This script must be run as root using 'sudo'
#
# Effects: Runs JOD on port 8080

# Checks that the script is being called as the super user
if [ $EUID -ne 0 ];
	then
	echo "This script must be run using sudo";
	exit 0;
fi

#Gets working directory
DIR="$(pwd)"

# Tries to set PIPE_PATH to commonly known locations for 'libjpipe.so' by checking if the path exists
PIPE_PATH1=/usr/lib/libreoffice/basis-link/ure-link/lib
PIPE_PATH2=/usr/lib/openoffice/basis-link/ure-link/lib
if [ -e "$PIPE_PATH1"/libjpipe.so ];
	then
		PIPE_PATH="$PIPE_PATH1"
elif [ -e "$PIPE_PATH2"/libjpipe.so ];
	then
		PIPE_PATH="$PIPE_PATH2"
else
	echo "Cannot find libjpipe.so file. Please set PIPE_PATH to directory path of libjpipe.so";
	exit 0;
fi

#### EDIT HERE IF NECESSARY SEE INSTRUCTIONS BELOW####
### Set PIPE_PATH to path of 'libjpipe.so' if script throws error "Cannot find libjpipe.so" ###
#PIPE_PATH=PATH TO DIRECTORY

#Sets Java library path
JLIB_PATH="$PIPE_PATH":"$DIR"/lib/hyperic-sigar-1.6.5/sigar-bin/lib
export JAVA_OPTS="-Djava.library.path="$JLIB_PATH""
echo "Java Library path is: "$JLIB_PATH""
echo "Working directory is: "$DIR""
cd jodconverter-webapp

#Removes any old JOD compiled files
if [ -e "$DIR"/jodconverter-webapp/target/jodconverter-sample-webapp-3.0-SNAPSHOT.war ];
	then
		echo "Removing old Compiled files"
		echo ""
		rm "$DIR"/jodconverter-webapp/target/jodconverter-sample-webapp-3.0-SNAPSHOT.war
fi
#Compiles the war file
echo ""
echo "Installing core libraries.."
echo ""
mvn install:install-file -DgroupId=org.artofsolving.jodconverter -DartifactId=jodconverter-core -Dversion=3.0-SNAPSHOT -Dpackaging=jar -Dfile="$DIR"/jodconverter-core-3.0-SNAPSHOT.jar
echo ""
echo "Compiling..."
echo ""
mvn -Djava.library.path=PIPE_PATH:"$DIR"/lib/hyperic-sigar-1.6.5/sigar-bin/lib compile war:war
#Exits back to root directory
cd ..

#Checks that JOD compiled correctly before continuing
if [ ! -e "$DIR"/jodconverter-webapp/target/jodconverter-sample-webapp-3.0-SNAPSHOT.war ];
	then
		printf "\nCompilation failed\n"
		exit 0;
fi

#Checks if app already exists. If it does it removes it
if [ -f "$DIR"/jodconverter-tomcat-2.2.2/webapps/converter.war ]
	then
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
		# Removes old converter if 
		echo "Removing old converter.."
		rm -rf "$DIR"/jodconverter-tomcat-2.2.2/webapps/converter
		rm "$DIR"/jodconverter-tomcat-2.2.2/webapps/converter.war
fi
#Copies it to tomcat directory
echo "Copying WAR file to tomcat"
cp "$DIR"/jodconverter-webapp/target/jodconverter-sample-webapp-3.0-SNAPSHOT.war "$DIR"/jodconverter-tomcat-2.2.2/webapps/converter.war 
#Starts up server
echo "Starting up server..."
cd "$DIR"/jodconverter-tomcat-2.2.2/bin/
./startup.sh
cd -

sleep 5

# Checks to see if openoffice pipes are open for conversions
instances="$(netstat | grep office[0-9])"
if [ ! -n "$instances" ];
	then
	printf "\nError Installation failed\n"
fi


