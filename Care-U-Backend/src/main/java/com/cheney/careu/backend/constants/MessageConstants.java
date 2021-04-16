// created by Srijan Pandey (sp3557@drexel.edu) at 20210305 23:02.
package com.cheney.careu.backend.constants;

public class MessageConstants {
    public final static Integer MESSAGE_IDENTIFIER_START_INDEX = 0;
    public final static Integer MESSAGE_IDENTIFIER_END_INDEX = 5; // there is a space between message identifier and message payload
    public final static Integer SEND_DATE_INDEX=  12;   //6-9
    public final static Integer RECORD_DATE_INDEX= 20; //10-13
    public final static Integer USER_ID_INDEX = 8;
    public final static Integer MESSAGE_ID_INDEX = 6;
    public final static Integer EMOTION_AND_STRENGTH_INDEX = 10; //10-11
    public final static Integer ACTIVITY_INDEX = 5;

    public final static Integer COMMAND_MESSAGE_START = 5;
    public final static Integer COMMAND_MESSAGE_END = 6;

    public final static String COMMAND_MESSAGE_TAG = "";
    public final static String DATA_MESSAGE_TAG= "";

    public final static String COMMAND_MESSAGE_TYPE = "01ffa";
    public final static String DATA_MESSAGE_TYPE = "01ffb";
    public final static String INVALID_MESSAGE_TYPE = "0000";

    public static final Character ALL_SERVICE = 'A';
    public static final Character VIDEO_SERVICE = 'B';
    public static final Character AUDIO_SERVICE = 'C';

    public static final String AUDIO_TAG = "AUDIO";
    public static final String VIDEO_TAG = "VIDEO";
    public static final String DEPTH_TAG = "DEPTH";
}
