// created by Srijan Pandey (sp3557@drexel.edu) at 20210305 23:00.
package com.cheney.careu.backend.services;

/**
 * This Class contains state information of services.
 * Using this class we can identify which services are
 * running and which are stopped .
 */
public class ServiceState {
    private static char bluetoothOn;
    private static boolean videoOn;
    private static boolean audioOn;
    private static boolean depthOn;

    public static boolean isVideoOn() {
        return videoOn;
    }

    public static boolean isAudioOn() {
        return audioOn;
    }

    public static boolean isDepthOn() { return depthOn; }

    public static void setVideoOn(boolean on) {
        videoOn = on;
    }

    public static void  setAudioOn(boolean on) {
        audioOn = on;
    }

    public static void setDepthOn(boolean on) { depthOn = on; }


}
