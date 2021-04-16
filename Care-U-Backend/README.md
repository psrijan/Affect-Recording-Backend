## Care-U-Backend

This code has two main components **main backend** and the **backend wrapper**. There are two types of messages that the Care-U-Backend code can accept. **DATA** and **COMMAND**. The data message contains emotion data sent from the mobile application. Likewise, the command message contains information about enabling or disabling of services (Depth/Audio/Video) of the vision apparatus. 

### Backend Wrapper 
The backend wrapper file This is the `BackendLowLevelWrapper`. For now this is an independent file that is currently available in the `backendwrapper` directory in the main folder. This is used to call the low level process `hcitool lescan` and `btmon` to capture the bluetooth beacon messages. 

### Main Backend
The main backend code is available inside `src/main` of the directory structure and contains the following components.
1. **Persistence Unit**: This is availavble in `persistence/PersistenceUnit.java` class. This unit stores the emotion data sent along from the mobile application into a file.
2. **Server Socket Connection**: This is available in the `socket/BackendSocket` class file and is used to establish connection betweeen `BackendLowLevelWrapper` and the **main backend** application.
3. **Encoding Decoding Logic**: This is the core decoding logic for the BLE GATT message. This is available in `parser/BLEMessageParser` class. It uses bit manipulation to capture data from GATT message in a data efficient way. 
4. **Services for Video/Audio/Depth**: This is available in `services/EnableService` code. This takes the commands that are configured in the `backend.properties` resourse file and enables/disables the afformentioend services as and when these requests are made by a command application.

I have put up comments for `DataProcessingModule` `BackendLowLevelWrapper` `PersistenceUnit` `EnableService` `BackendSocket` `BLEMessageParser` for more details.


## How Does Care-U-Backend Work?
The backend server needs to be run alongside the Care-U-Wrapper code. Backend Server has all the logic for enabling various audio, video devices. The signals for this is transfered using the `Care-U-Wrapper` project. Care-U-Wrapper is a low level interface that picks up the bluetooth broadcast messages and sends the valid messages over to the Care-U-Backend code.


# How to run Care-U-Backend? 
0. Create a folder called `/tmp/careu/data/`
1. Run THe `Care-U-Backend`	project using the run steps below
2. Goto the `Care-U-Wrapper` project and run the `Care-U-Wrapper` 
3. Current version of the project requires this sequence must be maintained for the code to run

Then run the `Care-U-Backend` project. 

	> mvn clean install 
	> cd target/ 
	> java -jar Care-U-Backend-1.0-SNAPSHOT-jar-with-depenedencies.jar 


# Message Interpretation:

The message is interpreted in the following way. If you goto the `MessageConstant.java` file, you will have an understanding of what each bit in the 16bit message signifies. 

Bits Representations: I will fill these details a bit later, but going through the `BLEMessageParser.java` and `MessageConstant.java` should make things clear (albeit, a bit tedious for now). 

(Documentation in progress)

### User Message Interpretation 
1. User ID: 
2. Message ID: (Bits 6-7) 
3. Send Date: 
4. Emotion and Strength: 
5. Activity Index: 

todo

### Command Message Interpretation
01ffa**X**000000000...

The char which is of 4 bits is represented as follows 

CHAR(**X**) = DEPTH(bit) AUDIO(bit) VIDEO(bit) Enable Request(bit)

If the bits 2-4 identifies the type of service to enable or disable. You can group services to take action on. 

The `Enable Request` bit identifies if the service needs to be enabled or disabled. 

At a given time you can either enable one or many services or disable one or many services.
 
You cannot enable a service and disable another service using the same message.

Rest of the bits at this time for command bits don't have any translations. 






