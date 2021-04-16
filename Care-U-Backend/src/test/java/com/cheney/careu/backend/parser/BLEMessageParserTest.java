package com.cheney.careu.backend.parser;

import com.cheney.careu.backend.dto.*;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class BLEMessageParserTest {
    public String[] checkVideoEnableDisable() {
        return new String[]{ "01ffa3f00000000000","01ffa2f00000000000"};
    }

    /**
     * 1st Message Enables Audio
     * 2nd Message Disables Audio
     * @return
     */
    public String[] checkAudioEnableDisable() {
        return new String[] { "01ffa5f00000000000","01ffa4f00000000000"};
    }

    /**
     * 1st Message Enables Depth
     * 2nd Message Disables Depth
     * @return
     */
    public String[] checkDepthEnableDisable() {
        return new String[] { "01ffa9f00000000000","01ffa8f00000000000"};
    }

    public String[] getUserData() {
        return new String[] {
                "01ffb0101000011110000000000000000000000000000",
                "01ffb01111001111000000000000000000000000000",
                "01ffb02121001212000000000000000000000000000"
        };
    }

    public String[] getUserDataForDate() {
        //000001767f0b5469
        return new String[] {"01ffb01010ff000001767f0b546900"};
    }

    public String[] getUserDataForEmotion() {
        //000001767f0b5469
        return new String[] {"01ffb0101011000001767f0b546900", "01ffb0101022000001767f0b546900"};
    }

    public String[] getUserDataForActivity() {
        //000001767f0b5469
        return new String[] {"01ffb1121233000001767f0b546900000", "01ffb2101022000001767f0b546900000"};
    }

    public String[] getDataForCompleteMessage() {
        return new String[] {"01ffb4121211000001767f0b546900", "01ffb2101022000001767f0b546900"};
    }

    @Test
    public void testVideoCommands() {
        CommandMessage videoEnable = BLEMessageParser.parseCommandMessage(checkVideoEnableDisable()[0]);
        CommandMessage videoDisable = BLEMessageParser.parseCommandMessage(checkVideoEnableDisable()[1]);
        assertEquals(videoEnable.getServiceType(), ServiceType.VIDEO_SERVICE);
        assertEquals(videoEnable.isEnableRequest() , true);
        assertEquals(videoDisable.getServiceType(), ServiceType.VIDEO_SERVICE);
        assertEquals(videoDisable.isEnableRequest(), false);
    }

    @Test
    public void testAudioCommands() {
        CommandMessage audioEnable = BLEMessageParser.parseCommandMessage(checkVideoEnableDisable()[0]);
        CommandMessage audioDisable = BLEMessageParser.parseCommandMessage(checkVideoEnableDisable()[1]);
        assertEquals(audioEnable.getServiceType(), ServiceType.AUDIO_SERVICE);
        assertEquals(audioEnable.isEnableRequest() , true);

        assertEquals(audioDisable.getServiceType(), ServiceType.AUDIO_SERVICE);
        assertEquals(audioDisable.isEnableRequest(), false);
    }

    @Test
    public void testDepthCommands() {
        CommandMessage depthEnable = BLEMessageParser.parseCommandMessage(checkVideoEnableDisable()[0]);
        CommandMessage depthDisable = BLEMessageParser.parseCommandMessage(checkVideoEnableDisable()[1]);
        assertEquals(depthEnable.getServiceType(), ServiceType.DEPTH_SERVICE);
        assertEquals(depthEnable.isEnableRequest() , true);
        assertEquals(depthDisable.getServiceType(), ServiceType.DEPTH_SERVICE);
        assertEquals(depthDisable.isEnableRequest(), false);
    }

    @Test
    public void testUserDataIds() {
        String[] dataMessage = getUserData();
        DataMessage dm = BLEMessageParser.parseDataMessage(dataMessage[0]);
        assertEquals(dm.getMessageId(), 1);
        assertEquals(dm.getUserId(), 1);

        DataMessage dmm = BLEMessageParser.parseDataMessage(dataMessage[1]);
        assertEquals(17, dmm.getMessageId());
        assertEquals(17, dmm.getUserId());

        DataMessage dmm1 = BLEMessageParser.parseDataMessage(dataMessage[2]);
        assertEquals(18, dmm1.getMessageId() );
        assertEquals(18, dmm1.getMessageId());

    }

    @Test
    public void testDataMessageTwo() {
        String[] dataMessage = getUserData();
        DataMessage dm = BLEMessageParser.parseDataMessage(dataMessage[1]);
        assertEquals(dm.getMessageId(), 2);
        assertEquals(dm.getUserId(), 2);
        assertEquals(dm.getEmotion(), Emotion.HAPPY);
        assertEquals(dm.getRecordedDate(), new Date(2019, 10, 19));
        assertEquals(dm.getReceivedDate(), new Date(2020, 10,19));
    }

    @Test
    public void testEncodeDate() {
        Date date = new Date();
        System.out.println("Initial Value: " + date.getTime());
        String encodedDate = BLEMessageParser.encodeDate(date.getTime());
        System.out.println("Encoded Date: " + encodedDate);
        long decodedDate = BLEMessageParser.decodeDate(encodedDate);
        System.out.println("Decoded Date: " + decodedDate);
        Date actual = new Date(decodedDate);
        assertEquals(date.getTime(), actual.getTime());
    }

    @Test
    public void testMessageDate() {
       String[] dataMessage = getUserDataForDate();
       Date date = new Date(1608449217641l);
       Date sentDate = BLEMessageParser.parseDataMessage(dataMessage[0]).getSentdate();
       assertEquals(date.getTime(), sentDate.getTime() );
    }

    @Test
    public void testEmotion() {
        Emotion emotion = BLEMessageParser.decodeEmotion("10");
        assertEquals(Emotion.HAPPY, emotion);
        assertEquals(0, emotion.getStrength());
        Emotion emotion1 = BLEMessageParser.decodeEmotion("21");
        assertEquals(Emotion.SAD, emotion1);
        assertEquals(1, emotion1.getStrength());
    }

    @Test
    public void testMessageForEmotion() {
       String[] dataMessage = getUserDataForEmotion();
       DataMessage message = BLEMessageParser.parseDataMessage(dataMessage[0]);
       Emotion emotion = message.getEmotion();
       assertEquals(Emotion.HAPPY, emotion);
       assertEquals(1, emotion.getStrength());

       DataMessage message1 = BLEMessageParser.parseDataMessage(dataMessage[1]);
       Emotion emotion1 = message1.getEmotion();
       assertEquals(Emotion.SAD, emotion1);
       assertEquals(2, emotion1.getStrength());
    }

    @Test
    public void testActivity() {
        long activity = BLEMessageParser.decodeStr("1", 0);
        Activity act = Activity.valueOf(activity);
        assertEquals(Activity.Walking, act);
    }

    @Test
    public void testMessageForActivity() {
        String[] dataMessage = getUserDataForActivity();
        DataMessage message = BLEMessageParser.parseDataMessage(dataMessage[0]);
        Activity activity = message.getActivity();

        Activity activity1 = BLEMessageParser.parseDataMessage(dataMessage[1]).getActivity();
        assertEquals(Activity.Walking, activity);
        assertEquals(Activity.Running , activity1);
    }

    @Test
    public void testOverallbActivity() {
        String[] dataMessage = getDataForCompleteMessage();
        //"01ffb1121211000001767f0b546900", "01ffb2101022000001767f0b546900";
        DataMessage message = BLEMessageParser.parseDataMessage(dataMessage[0]);
        assertEquals(33, message.getMessageId() );
        assertEquals(33, message.getUserId() );
        Date expected = new Date(1608449217641l);
        Date actual = message.getSentdate();
        assertEquals(expected, actual);

        Emotion emotionActual = message.getEmotion();
        Emotion emotionExpected = Emotion.HAPPY;
        assertEquals(emotionExpected, emotionActual);
        assertEquals(1, emotionActual.getStrength());

        Activity activityActual = message.getActivity();
        Activity activityExpected = Activity.Eating;
        assertEquals(activityExpected, activityActual);
    }

}