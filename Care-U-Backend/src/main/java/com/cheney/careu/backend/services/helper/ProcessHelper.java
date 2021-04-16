package com.cheney.careu.backend.services.helper;

import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.slf4j.Logger;

/**
 * Helper Class for creation of processes
 */
public class ProcessHelper {

    private Process process;
    private final String command;
    private static final int MAX_BUFFER_LENGTH = 100;

    private final Logger logger = LoggerFactory.getLogger(ProcessHelper.class);

    public ProcessHelper(String command) {
        this.command = command;
    }

    public void enable() throws IOException {
        process = new ProcessBuilder(command.split(" ")).redirectErrorStream(true).start();
//        captureLog();
    }

    public void disable() {
        process.destroy();
    }

//    public void captureLog() {
//        Runnable runnable = () -> {
//            InputStream inputStream= process.getInputStream();
//            char[] buffer = new char[MAX_BUFFER_LENGTH];
//            int offset = -1;
//            int length = -1;
//            try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream)){
//                while (-2 < (length = inputStreamReader.read(buffer, offset, MAX_BUFFER_LENGTH))) {
//                    System.out.println(String.valueOf(buffer)); // change this to log
//                }
//            } catch (IOException ex ) {
//                logger.debug("IO Exception in Log Capture: " , ex);
//            }
//        };
//        Thread thread = new Thread(runnable);
//        thread.start();
//    }

}
