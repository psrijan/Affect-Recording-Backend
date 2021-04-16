package com.cheney.careu.backend.services.base;

import com.cheney.careu.backend.services.ServiceState;
import com.cheney.careu.backend.services.astra.AstraWrapperMain;
import com.cheney.careu.backend.utilities.Config;

/**
 * Author: Srijan Pandey (sp3557@drexel.edu)
 * Depth Service class that handles processing depth information from sensors
 */
public class DepthService implements IService {

    private AstraWrapperMain astraMain; // Main wrapper class that encapsulates access to ASTRA SDK

    public DepthService(String tag) {
        astraMain = new AstraWrapperMain(tag, Config.getDataDirectory());
    }


    @Override
    public void enable() {
        logger.debug("Enabling Depth Service...");
        astraMain.startRecordingDepth();
        ServiceState.setDepthOn(true);
    }

    @Override
    public void disable() {
        logger.debug("Disabling Depth Service...");
        astraMain.disable();
        ServiceState.setDepthOn(false);
    }
}
