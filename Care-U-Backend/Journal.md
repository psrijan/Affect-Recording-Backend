Sat (27/3)
1. Figured out the Wifi issue
2. Worked for sometime to figure out the bluetooth low energy linux configuration

3. Worked on separating out the Wrapper code into a separate code with much cleaner interfaces

4. Started looking into OpenNI and Astra SDKS to figure out what they are all about. I would say the most important descriptions are available in the readme. Readme gives you 
an idea bout how to build the project examples. This gives you a way to interface with the depth and infrared sensors. Also a high level java 
jar is built duirng this process for openni libraries, I think this can be used to integrate things in the java project. 

5. I looked into the Astra examples too. Installed Cmake, pkgconfig, lib(something) into the persee device. cmake took such a long time, probably armv7l processors lack of grunt?
6. Calling it a day!!

Sunday (28/3)
6. Then I tried to build the astra examples. This gave a segmentation fault. Need to figure out why this is though. It's kind of cool to actually, work with the niti grities of cpp code.
7. Setting up the opencv environment in my own computer and persee to make sure keis AstraDemo code works alongside my code.  
8. Fixed compile time error for opencv 
9. Fixed maven gav issue for opencv and astrasdk
9. Completed Integrating Keis code with my own code. Fixed all library related issues with this code.
10. Integration Test for all three of the services (This is non bluetooth test for the services) 
12. The Video, Audio, Text recording work fine on Persee setup with integration tests (no bluetooth as of now)
11. During integration test Keis code doesn't find a FrameListener inside the StreamReader. Need to look more into this. 
12. Tried to configure the bluetooth bluez with the latest version: There is a slight issue with enabling bluetooth low energy beacon mode in persee. 
13. Calling it a day!! Have a requirement exemption quiz for which I need to complete a full coursework worth of material by Wednesday.
