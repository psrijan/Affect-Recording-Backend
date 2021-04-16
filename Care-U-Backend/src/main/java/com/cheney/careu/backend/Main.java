// created by Srijan Pandey (sp3557@drexel.edu) at 20210305 23:01.
package com.cheney.careu.backend;

import com.cheney.careu.backend.core.DataProcessingModule;
import com.cheney.careu.backend.socket.BackendSocket;
import com.cheney.careu.backend.utilities.Config;
import com.cheney.careu.backend.utilities.ConfigurationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Main {
    private BackendSocket backendSocket;
    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.init();
    }

    private void init() {
        logger.info("Care U Backend - Starting Up...");
        try {
            ConfigurationManager configurationManager = new ConfigurationManager();
            configurationManager.getPropertyValue();
            logger.debug("Name {}" , Config.getAppName());
            backendSocket = new BackendSocket(new DataProcessingModule());
            backendSocket.enableBackendSocket();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        logger.info("Care U Backend - Startup Complete...");
    }
}
