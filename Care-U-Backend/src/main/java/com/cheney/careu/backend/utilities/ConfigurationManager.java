package com.cheney.careu.backend.utilities;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import static com.cheney.careu.backend.constants.MessageConstants.*;


/**
 * Author: Srijan Pandey (sp3557@drexel.edu)
 * Get configuration data into the application to provide 
 * for scripts and additional configuration features.
 */
public class ConfigurationManager {
    public static final String PROPERTY_FILE_NAME = "backend.properties";

    public void getPropertyValue() throws IOException {
        InputStream inputStream =null;
        try {
            Properties prop = new Properties();
            inputStream = getClass().getClassLoader().getResourceAsStream(PROPERTY_FILE_NAME);
            if (inputStream == null)
                throw new FileNotFoundException("Property File not Now Found");
            prop.load(inputStream);
            Config.setAppName(prop.getProperty("name"));
            Config.setVideoCommand(prop.getProperty("videoService"));
            Config.setDepthCommand(prop.getProperty("depthService"));
            Config.setAudioCommand(prop.getProperty("audioService"));
            Config.setDataDirectory(prop.getProperty("data_directory"));
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }
    }
}

