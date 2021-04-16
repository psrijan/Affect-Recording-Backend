#!/bin/bash -x 
mvn -e install:install-file
-Dfile=`pwd`/ble-java-0.1.jar
-DgroupId=it.tangodev
-DartifactId=ble-java
-Dversion=0.1
-Dpackaging=jar
-DgeneratePom=true
