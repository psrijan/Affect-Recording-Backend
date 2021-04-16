package com.cheyney.careu.wrapper;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.Thread;


public class BtmonService implements Runnable {
    private Process btmonProcess;
    private DataOutputStream careuOutputStream; // Data output stream to send data to careu


    public void setOutputStream(DataOutputStream dos) {
        this.careuOutputStream= dos;
    }
    /**
     * Enables the Btmon service by calling the low level apis and captures the beacon message
     * from the btmon service
     */
    private void enableBtmon() {
        System.out.println("Starting Btmon Service...");
        try {
            btmonProcess = new ProcessBuilder("/usr/bin/btmon").start();
            InputStreamReader inputStream = new InputStreamReader(btmonProcess.getInputStream());
            int size;
            char buffer[] = new char[1024];
            while ((size = inputStream.read(buffer, 0, 1024)) != -1) {
                // System.out.println(" --- Read ----");
                String data = String.valueOf(buffer, 0, size);
                processBeaconMessage(data);
                // Thread.sleep(1000);
            }
        } catch (IOException ex ) {
            ex.printStackTrace();
        } catch (Exception ex ) {
            ex.printStackTrace();
        } finally {
            if (btmonProcess != null)
                btmonProcess.destroy();
        }
    }

    /**
     * The beacon message are validity checked. The filterBleMessage acts as
     * a filter to check the regex value of care-u ble messages. (some work pending on that though)
     * @param dataChunk
     */
    public void processBeaconMessage(String dataChunk) {
        String bleMessage = filterBleMessage(dataChunk);
        if (bleMessage.length() == 0) {
            System.out.println("No received data");
            return;
        }

        if (careuOutputStream == null) {
            System.out.println("The socket connection is not yet open");
            return;
        }

        //System.out.println("Processing Data: " + bleMessage);
        try {
            careuOutputStream.writeUTF(bleMessage);
        } catch (IOException ex ) {
            ex.printStackTrace();
        }
    }


    /**
     * Checks regex pattern to identify care u message.
     * @param line
     * @return
     */
    public String filterBleMessage(String line) {
        System.out.println("Filtering messages...");
        String regexStr = "\\s+Data:\\s[a-zA-Z0-9]{20,100}";
        String regexStrCur = "\\s+Data:\\sbeac[a-zA-Z0-9]{20,100}";
        String regx1 = "Service Data \\(UUID [0-9a-zA-Z]{4,10}\\): [A-Za-z0-9]{20,40}";
        String regx2 = "\\s*Service\\sData\\s\\(UUID [a-zA-z0-9]{10}\\): [a-zA-z0-9]{32,50}";
        String serviceDataRegex = "Service\\sData\\s\\(UUID [a-zA-z0-9]{2,10}\\): [a-zA-z0-9]{32}";
        Pattern pattern = Pattern.compile(regexStrCur);
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            System.out.println("Regex Matched...");
            int start = matcher.start();
            int end = matcher.end();
            line = line.substring(start, end);
            line = line.trim();
            line = line.replace("Data: beac", "");
            System.out.println("Encoded Data: " + line);
            return line.trim();
        } else {
            System.out.println("Didn't Match anything");
            return "";
        }
    }


    public void run() {
        enableBtmon();
    }
}
