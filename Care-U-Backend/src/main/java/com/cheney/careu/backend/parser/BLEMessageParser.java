// created by Srijan Pandey (sp3557@drexel.edu) at 20210305 22:57.
package com.cheney.careu.backend.parser;

import com.cheney.careu.backend.constants.MessageConstants;
import com.cheney.careu.backend.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.cheney.careu.backend.constants.MessageConstants.*;
/**
 * This is the BLE Parser logic that handles 
 * the bit calculation for the 16 byte GATT message. 
 */
public class BLEMessageParser {

    private static Logger logger = LoggerFactory.getLogger(BLEMessageParser.class);
    private static final Integer HEX_RADIX = 16;
    private static final Integer MAX_BITS_IN_DATE = 24;
    private static final Integer OFFSET_CHANGE_PER_TUPLE = 4;

    /**
     * Takes the first 6 bytes from the ble message to identify the type of message
     * This checks that the first six message matches the valid regex for DATA/COMMAND
     * and makes a decision on what type of message is coming to the server.
     * @param beaconMessage
     * @return
     */
    public static String getMessageType(String beaconMessage) {
        return beaconMessage.substring(MESSAGE_IDENTIFIER_START_INDEX, MESSAGE_IDENTIFIER_END_INDEX);
    }

    /**
     * Start of 6th character is the command message. To minimize the data usage it uses just 4 bits of data to
     * identify if it is an enable/disable request and what services (video,audio,depth) to control.
     * 1st bit Enable/Disable request
     * 2nd bit -> Audio
     * 3rd bit -> Video
     * 4th bit -> Depth
     * @param encodedMessage
     * @return
     */
    public static CommandMessage parseCommandMessage(String encodedMessage) {
        CommandMessage commandMessage = new CommandMessage();
        String commandVal = encodedMessage.substring(COMMAND_MESSAGE_START, COMMAND_MESSAGE_END);
        logger.debug("Parser Command Message: {}", commandVal);
        int command = Integer.parseInt(commandVal , 16);
        logger.debug("Parser Command Message in Integer {}", command);
        boolean isEnableRequest = (command & 0x1) == 1;
        boolean isVideoOn = ((command >> 1) & 0x1) == 1;
        boolean isAudioOn = ((command >> 2) & 0x1) == 1;
        boolean isDepthOn = ((command >> 3) & 0x1) == 1;
        commandMessage.setEnableRequest(isEnableRequest);
        if (isAudioOn && isVideoOn && isDepthOn) {
            commandMessage.setServiceType(ServiceType.ALL);
        } else if (isAudioOn) {
            commandMessage.setServiceType(ServiceType.AUDIO_SERVICE);
        } else if (isVideoOn) {
            commandMessage.setServiceType(ServiceType.VIDEO_SERVICE);
        } else if (isDepthOn) {
            commandMessage.setServiceType(ServiceType.DEPTH_SERVICE);
        }

        commandMessage.setEnableRequest(isEnableRequest);
        System.out.println(commandMessage.toString());
        return commandMessage;
    }

    /**
     * Main function to parse data message. Gets various indexs where particular
     * data is present in the BLE GATT message (Please find that in Constants file)
     * and parses them.
     * @param encodedMessage
     * @return
     */
    public static DataMessage parseDataMessage(String encodedMessage) {
        String messageIdStr = encodedMessage.substring(MESSAGE_ID_INDEX, MESSAGE_ID_INDEX + 2);
        String userIdStr = encodedMessage.substring(USER_ID_INDEX , USER_ID_INDEX + 2);
        String sendDateStr = encodedMessage.substring(SEND_DATE_INDEX, SEND_DATE_INDEX + 16);
        //String recordedDateStr = encodedMessage.substring(RECORD_DATE_INDEX, RECORD_DATE_INDEX + 8);
        String emotionStr = encodedMessage.substring(EMOTION_AND_STRENGTH_INDEX, EMOTION_AND_STRENGTH_INDEX + 2);
        String activityStr = encodedMessage.substring(ACTIVITY_INDEX, ACTIVITY_INDEX + 1);

        long sendDateMillis = decodeDate(sendDateStr);
        //long recordedDateMillis = decodeDate(recordedDateStr);

        Date sendDate = new Date(sendDateMillis);
        //Date recordedDate = new Date(recordedDateMillis);

        DataMessage messageDTO = new DataMessage();
        messageDTO.setSentdate(sendDate);
        //messageDTO.setReceivedDate(recordedDate);

        long messageId = decodeStr(messageIdStr, 0);  // Takes only 1 byte of data for messageId (max 127 messages)
        long userId = decodeStr(userIdStr, 0);
        long activity = decodeStr(activityStr, 0);

        messageDTO.setMessageId(messageId);
        messageDTO.setUserId(userId);
        messageDTO.setEmotion(decodeEmotion(emotionStr));
        messageDTO.setActivity(decodeActivity(activityStr));
        return messageDTO;
    }

    /**
     * Takes the first byte of activity string and do a bitwise AND operation with 15
     * and pass it to the Activity Enum to get the type of activity it is. Using 1 byte of
     * data at the moment for extensibility can be further shrunk.
     * @param activityStr
     * @return
     */
    private static Activity decodeActivity(String activityStr) {
        long activityIndex = (long) (activityStr.toCharArray()[0] & 0x0F);
        return Activity.valueOf(activityIndex);
    }

    /**
     * Emotion is the first 4 bits of the emotionStr[0]
     * Emotion Strength: is the next 4 bits of the emotionStr[0]
     * This is found using bitwise operator and bitwise AND with 0x0F
     * @param emotionStr
     * @return
     */
    protected static Emotion decodeEmotion(String emotionStr) {
        char emotionChar = emotionStr.toCharArray()[0];
        long emotion = Long.parseLong(emotionChar + "" , HEX_RADIX);
        char strengthChar = emotionStr.toCharArray()[1];
        long strength = Long.parseLong(strengthChar + "", HEX_RADIX);
        Emotion emoEnum = Emotion.valueOf(emotion);
        emoEnum.setStrength(strength);
        return emoEnum;
    }

    /**
     * Little Endian
     * Uses 4 bytes of the GATT protocol data. This is denoted by the i in the
     * particular code. So this code takes a unix timestamp and converts it into
     * GATT encoded message.
     * Takes a 4 byte date value, offsets it by 4 bits and
     * converts it to hexadecimal string value and appends it the encodedDate value
     * @param date
     * @return
     */
    public static String encodeDate(long date) {
        String hexa = Long.toHexString(date);
        String padding = "";
        if (hexa.length() < 16) {
            for (int i = 0; i < 16 - hexa.length(); i++) {
                padding += "0";
            }
        }
        return padding + hexa;
//        StringBuilder dateBuilder = new StringBuilder();
//        for (int offset = 0, i = 0; i < 8; i++, offset += 4) {
//            int cur = ((int) (date >> offset) & 0xF);
//            String hexTuple = Integer.toHexString(cur);
//            dateBuilder.append(hexTuple);
//        }
//        System.out.println(dateBuilder.toString());
//        return dateBuilder.toString();
    }

    /**
     * Decode Date is similar to encode but in reverse direction
     * @param encodedDate
     * @return
     */
    public static long decodeDate(String encodedDate) {
        long val = Long.parseLong(encodedDate, 16);
        return val;
//        char[] chaArr = encodedDate.toCharArray();
//        long date = 0;
//        for (int offset = MAX_BITS_IN_DATE, i = 0; i < chaArr.length; i++, offset -= OFFSET_CHANGE_PER_TUPLE) {
//            long d = ((Long.parseLong(chaArr[i]+"", HEX_RADIX)) << offset) & Long.MAX_VALUE;
//            date = date | d;
//        }
//        logger.debug("Decoded Date: {} " , date);
//        return date;
    }

    /**
     * Decoding in Little Endian way : LSB in the smallest index
     * @param encodedStr
     * @param defaultOffset
     * @return
     */
    public static long decodeStr(String encodedStr, int defaultOffset) {
        logger.debug("Encoded String: {}", encodedStr);
        char[] chaArr = encodedStr.toCharArray();
        long val = 0;
        for (int offset = defaultOffset, i = 0; i < chaArr.length; i++, offset += 4) {
            long d = (Long.parseLong(chaArr[i]+"",  HEX_RADIX) << offset) & Long.MAX_VALUE;
            val = val | d;
        }
        return val;
    }

}
