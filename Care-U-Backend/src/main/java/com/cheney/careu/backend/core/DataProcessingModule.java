// created by Srijan Pandey (sp3557@drexel.edu) at 20210305 23:02.
package com.cheney.careu.backend.core;

import com.cheney.careu.backend.constants.MessageConstants;
import com.cheney.careu.backend.dto.CommandMessage;
import com.cheney.careu.backend.dto.DataMessage;
import com.cheney.careu.backend.parser.BLEMessageParser;
import com.cheney.careu.backend.persistence.PersistenceUnit;
import com.cheney.careu.backend.services.ServiceHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Author: Srijan Pandey (sp3557@drexel.edu | p.srijan08@gmail.com)
 * This is the first layer of main-backend application
 * The function of the class is to facilitate data processing
 * For instance, this class filters out DataMessage, CommandMessage
 * sent out from the client application and passes these messages to
 * the respective flow. It also ensures that non Care-U beacon messages
 * are discarded.
 */
public class DataProcessingModule {
    private final PersistenceUnit pu;
    private final ServiceHandler service;

    private final Logger logger = LoggerFactory.getLogger(DataProcessingModule.class);

    public DataProcessingModule() {
        pu = new PersistenceUnit();
        service = new ServiceHandler();
    }

    /**
     * Based on the type of message move the flow of application to those class files.
     */
    public void processData(String beaconMessage) {
        logger.debug("Beacon Message: {} ", beaconMessage);
        String msgType = BLEMessageParser.getMessageType(beaconMessage);

        if (msgType.equalsIgnoreCase(MessageConstants.DATA_MESSAGE_TYPE)) {
            DataMessage dataMessage = BLEMessageParser.parseDataMessage(beaconMessage); // Parser that has decoding logic for data message
            pu.saveToFile(dataMessage);
        } else if (msgType.equals(MessageConstants.COMMAND_MESSAGE_TYPE)) {
            CommandMessage commandMessage = BLEMessageParser.parseCommandMessage(beaconMessage); // Parser code that has decoding code for command message
            if (commandMessage.isEnableRequest())
                service.enableService(commandMessage.getServiceType());
            else
                service.disableService(commandMessage.getServiceType());
        } else {
            logger.error("Message {} is of Invalid Message type " , beaconMessage);
        }
    }



}
