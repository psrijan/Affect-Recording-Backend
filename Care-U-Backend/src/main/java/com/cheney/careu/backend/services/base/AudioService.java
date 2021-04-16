// created by Srijan Pandey (sp3557@drexel.edu) at 20210305 22:58.
package com.cheney.careu.backend.services.base;

import com.cheney.careu.backend.services.ServiceState;
import com.cheney.careu.backend.services.helper.ProcessHelper;

import java.io.IOException;
/**
 * Author: Srijan Pandey (sp3557@drexel.edu)
 * Audio Service class that handles processing of audio information
 */
public class AudioService implements IService {

    private final ProcessHelper processHelper;
    private final String tag;

    public AudioService(String tag, String command) {
        this.tag = tag;
        processHelper = new ProcessHelper(command);
    }

    @Override
    public void disable() {
        logger.debug("Disabling Audio Service...");
        processHelper.disable();
        ServiceState.setAudioOn(false);
    }

    @Override
    public void enable() {
        logger.debug("Enabling Audio Service");
        try {
            processHelper.enable();
            ServiceState.setAudioOn(true);
        } catch (IOException ex ) {
            ServiceState.setAudioOn(false);
        }
    }
}
