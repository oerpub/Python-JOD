#Author: Gbenga Badipe
#Date: 4/24/2012

## Important ##
The global variable JAVA_HOME should be set to point to your JAVA installation

########### INSTALLATION INSTRUCTIONS ###########
1. Configure JOD's parameters in a config file called "config.properties" located in: 'jodconverter-tomcat-2.2.2/bin/'

2. Open install.sh and confirm that the path to your open office libraries match with '/usr/lib/libreoffice/basis-link/ure-link/lib'. If it does not
   you will have to modify the path assigned to the variable PIPE_PATH in 'install.sh' to point to your open office libraries.

3. Install JOD by running the script 'install.sh' in the root directory using the command: 
   sudo ./install.sh

4. Check localhost:8080/converter/ to confirm the converter is running


########### PYTHON SCRIPT INSTRUCTIONS ###########

To execute batch conversion use the format:
----------------------------------------------
python convert.py -b [testbed directory]  [output directory] [number of documents] [format of documents] [output format]

Example: python convert.py -b /home/foo/ /home/foo/outputdir 3 doc odt

To execute a single file conversion use format:
-----------------------------------------------
python convert.py [file location] [output_directory] [format] [output format]

Example: python convert.py ~/foo.doc ~/outputdir/ doc odt


########### Startup and Shutdown of JOD ###########

Startup and shutdown scripts are also included in the root directory. Please use them to startup and shutdown JOD after installation.
