// created by Srijan Pandey (sp3557@drexel.edu) at 20210305 23:00.
package com.cheney.careu.backend.services;

import com.cheney.careu.backend.dto.ServiceType;
import com.cheney.careu.backend.exception.ServiceException;
import com.cheney.careu.backend.services.base.IService;
import com.cheney.careu.backend.services.base.ServiceFactory;
import com.cheney.careu.backend.utilities.FileNameUtil;
import com.cheney.careu.backend.utilities.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.cheney.careu.backend.constants.MessageConstants.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 * Author: Srijan Pandey (sp3557@drexel.edu)
 * This class handles enabling and disabling of services. There are three types 
 * of service that is available in CareU-Backend (Audio, Video and Depth service)
 * which are all mediated by this ahndler.
 */
public class ServiceHandler {
    private Map<String, IService> serviceMap= new HashMap<>();
    private Map<String, String> commandMap = new HashMap<>();

    private Logger logger = LoggerFactory.getLogger(ServiceHandler.class);

    public ServiceHandler() {
        commandMap.put(VIDEO_TAG , Config.getVideoCommand());
        commandMap.put(AUDIO_TAG, Config.getAudioCommand());
        commandMap.put(DEPTH_TAG, Config.getDepthCommand());
    }

    /**
     * Checks that the service that has been requested is not already disabled
     * and disables the service. Either all service disable request can be sent,
     * or in the case of a  failure a reerquest for individual service can also
     * be sent.
     * @param serviceType
     */
    public void disableService(ServiceType serviceType) {
        // log needs to be put here
        logger.debug("Service Type: Enable: {}", serviceType.getServiceDescriptor());
        if (ServiceState.isAudioOn() && (serviceType.equals(ServiceType.ALL) || serviceType.equals(ServiceType.AUDIO_SERVICE))) {
            ServiceState.setAudioOn(false);
            disableSpecificService(AUDIO_TAG);
        }

        if (ServiceState.isVideoOn() && (serviceType.equals(ServiceType.VIDEO_SERVICE) || serviceType.equals(ServiceType.ALL))) {
            ServiceState.setVideoOn(false);
            disableSpecificService(VIDEO_TAG);
        }

        if (ServiceState.isDepthOn() && (serviceType.equals(ServiceType.DEPTH_SERVICE) || serviceType.equals(ServiceType.ALL))) {
            ServiceState.setDepthOn(false);
            disableSpecificService(DEPTH_TAG);
        }
    }


    /**
     *  Same idea as the disable request
     * @param serviceType
     */
    public void enableService(ServiceType serviceType) {
        // Log Things that are enabled and things that the client is requesting to be opened
        if (!ServiceState.isAudioOn() && (serviceType.equals(ServiceType.ALL) || serviceType.equals(ServiceType.AUDIO_SERVICE))) {
            ServiceState.setAudioOn(true);
            enableSpecificService(AUDIO_TAG);
        }

        if (!ServiceState.isVideoOn() && (serviceType.equals(ServiceType.VIDEO_SERVICE) || serviceType.equals(ServiceType.ALL))) {
            ServiceState.setVideoOn(true);
            enableSpecificService(VIDEO_TAG);
        }

        if (!ServiceState.isDepthOn() && (serviceType.equals(ServiceType.DEPTH_SERVICE) || serviceType.equals(ServiceType.ALL))) {
            ServiceState.setDepthOn(true);
            enableSpecificService(DEPTH_TAG);
        }
    }

    /**
     * Process when they are enabled are stored in a map. We can
     * pick the enabled process based on the tag that is currently
     * sent and disable it.
     * @param tag
     */
    private void disableSpecificService(String tag) {
        logger.debug("Current Disable Tag: {} ", tag);
        IService service = serviceMap.get(tag);
        serviceMap.remove(service);
        service.disable();
    }

    /**
     * When a enable Process is called, the code picks up the command from the
     * Config and runs it. (Yep these commands are configurable in backend.properties)
     * Once the process has been started the Processes are kept in a Map for disabling
     * or future reference of the code.
     * @param tag
     */
    public void enableSpecificService(String tag) {
        logger.debug("Current Enable Tag: {} ", tag);
        String command = commandMap.get(tag).trim();
        String dataDirectory = Config.getDataDirectory();
        command = FileNameUtil.formatCommand(command, tag , dataDirectory);
        try {
            IService service = ServiceFactory.getService(tag, command);
            service.enable();
            serviceMap.put(tag , service);
        } catch (ServiceException ex ) {
            logger.error("Service Issue: ", ex);
        }
    }
}
