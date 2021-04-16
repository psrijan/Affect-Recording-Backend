package com.cheyney.careu.wrapper;

// enum MockMessagesRefactored {
//     VideoEnable("", "Enables Video Service"),
//     VideoDisable("", "Disables Video Service"); 

//     private String messasge;
//     private String description;

//     MockMessagesRefactored(String message, String description) {
//         this.message= message;
//         this.description = description;
//     }

//     public String getDescription() {
//         return this.description;
//     }

//     public String getMessage() {
//         return this.messasge;
//     }
// }

public class MockMessages {
    /**
     * This function contains the list of messages that is used to test out 
     * the functionality of the Care-U-Backend code.
     * @return
     */ 
    public static String[] overallMessage() {
        return new String[] {
                "01ffb1101011000001767f0b546900", // Data message 1
                "01ffb2101022000001767f0b546900", // Data Message 2
                "01ffa3f00000000000000000000000", // Video Enable Message
                "01ffa2f00000000000000000000000", // Video Disable Message
                "01ffa5f00000000000000000000000", // Audio Enable Message
                "01ffa4f00000000000000000000000", // Audio Disable Message
                // "01ffa9f00000000000000000000000", // Depth Enable Message
                // "01ffa8f00000000000000000000000", // Depth Disable Message
        };
    }

}