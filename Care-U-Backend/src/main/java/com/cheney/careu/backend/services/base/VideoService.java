// created by Srijan Pandey (sp3557@drexel.edu) at 20210305 23:02.
package com.cheney.careu.backend.services.base;

import com.cheney.careu.backend.services.ServiceState;
import com.cheney.careu.backend.services.helper.ProcessHelper;

import java.io.IOException;
/**
 * Author: Srijan Pandey (sp3557@drexel.edu)
 * This is video specific service enabling class.
 * The complexity of handling threads and Process Builder is 
 * handled by the internal abstract service class. 
 */
public class VideoService implements IService {

    private final ProcessHelper processHelper;

    public VideoService(String tag, String command) {
        processHelper = new ProcessHelper(command);
    }

    @Override
    public void enable() {
        logger.debug("Enabling Video Service...");
        try {
            processHelper.enable();
            ServiceState.setVideoOn(true);
        } catch (IOException ex ) {
            logger.error("Error: ", ex);
            ServiceState.setVideoOn(false);
        }
    }

    @Override
    public void disable() {
        logger.debug("Disabling Video Service...");
        processHelper.disable();
        ServiceState.setVideoOn(false);
    }

//    public static void main (String[] args ) {
//
//        String command = "/usr/bin/ffmpeg -y -thread_queue_size 100 -f pulse -ac 2 -i default -acodec pcm_s16le -f v4l2 -video_size 640x480 -r 30 -vcodec mjpeg -i /dev/video0 -acodec pcm_s16le -vcodec mjpeg -q:v 3 /home/srijan/careu/first.mkv";
//        String command3 = "/usr/bin/ffmpeg -i /home/srijan/careu/input.mp4 /home/srijan/careu/output.mp4";
//        try {
//            Process process = new ProcessBuilder(command.trim().split(" ")).redirectErrorStream(true).start();
//            captureLog(process);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    private static void captureLog(Process process) {
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                InputStream inputStream= process.getInputStream();
//                char[] buffer = new char[100];
//                int offset = 0;
//                int length = 0;
//                try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream)){
//                    while (-1 < (length = inputStreamReader.read(buffer, offset, 100))) {
//                        System.out.println(String.valueOf(buffer)); // change this to log
//                    }
//                } catch (IOException ex ) {
//                    ex.printStackTrace();
//                }
//            }
//        };
//        Thread thread = new Thread(runnable);
//        thread.start();
//    }
}
