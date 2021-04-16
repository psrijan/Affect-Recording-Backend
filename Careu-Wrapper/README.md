# CAREU WRAPPER
This is the wrapper application for connection with low level bluetooth interface using Bluez.

## Description

The way this works is by opening a process to the bluetooth connection 
using bluez. This is done by two low level commands namely.

    sudo hcitool lescan 
    sudo btmon 

The first command opens up a process to receive bluetooth broadcast messages.

The second command opens a process that reads off the broadcast message in a 
user friendly way.

When these processes are enabled, the java application takes in the input of the second process and sends it across a socket to `CAREU-BACKEND` application.

There is a regex message filter written to filter out only those messages that adhere to the message specifications of the `CAREU-BACKEND` application. There might be need in future to change this regex to adjust for the type of GATT message that is received from the mobile application.  
 

## Steps to run:
1. Install Bluez in ubuntu
2. Install maven to build the application.
3. Go to the home project of this application and run  


    mvn clean install 
 
4. Now goto `target` and run the jar file using the following command. 


    > java -jar Careu-Wrapper-1.0-SNAPSHOT.jar [test]

Give an argument of test if you want to run the `Careu-Wrapper` to run in test mode. This will send out mock messages to the `Care-U-Backend` code to ensure that all services are running fine.

Note: You need to have `CARE-U-BACKEND` running before running the application. As of now you will have to restart both the application if you don't do this