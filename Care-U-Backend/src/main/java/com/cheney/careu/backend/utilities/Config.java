// created by Srijan Pandey (sp3557@drexel.edu) at 20210305 23:00.
package com.cheney.careu.backend.utilities;

/**
 * Author: Srijan Pandey (sp3557@drexel.edu)
 * Configuration DTO
 */
public class Config {
    private static String appName;
    private static String depthCommand;
    private static String videoCommand;
    private static String audioCommand;
    private static String dataDirectory;

    public  static void setDepthCommand(String command) {
        depthCommand = command;
    }

    public static void setVideoCommand(String command) {
        videoCommand = command;
    }

    public static void setAudioCommand(String command) {
        audioCommand = command;
    }

    public static String getAppName() {
        return appName;
    }

    public static void setAppName(String appName) {
        Config.appName = appName;
    }

    public static void setDataDirectory(String dd) {
        dataDirectory = dd;
    }

    public static String getAudioCommand() {
        return audioCommand;
    }

    public static String getVideoCommand() {
        return videoCommand;
    }

    public static String getDepthCommand() {
        return depthCommand;
    }

    public static String getDataDirectory() {return  dataDirectory; }
}
