package com.cheyney.careu.wrapper;

import java.io.*;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.Thread;

public class HciService implements Runnable{

    private Process hciProcess;
    private DataOutputStream socketOutputStream;

    /**
     * Enables the hcitool lescan process
     * Some lingering code comments kept for future modification.
     */
    private void enableHciScan() {
        try {
            hciProcess= new ProcessBuilder("/usr/bin/hcitool", "lescan").start();
//            OutputStreamWriter output = new OutputStreamWriter(hciProcess.getOutputStream());
//            InputStreamReader errorStream = new InputStreamReader(hciProcess.getErrorStream());
//            InputStreamReader input = new InputStreamReader(hciProcess.getInputStream());
//            int size;
//            char buffer[] = new char[1024];
//            while ((size = input.read(buffer, 0, 1024)) != -1) {
//                System.out.println("--- Read --- ");
//                if(size == 0)
//                    continue;
//                String data = String.valueOf(buffer, 0, size);
//                System.out.println("Data: " + data);
//            }

            //while ((size = errorStream.read(buffer, 0, 1024)) != -1) {
            //	   System.out.println(" --- Read Error ---- ");
            //	   if (size == 0)
            //		   continue;
            //	   String data = String.valueOf(buffer, 0, size);
            //	   System.out.println(" Error Data: "  + data);
            //  }
            System.out.println("--- End ---");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
             if (hciProcess != null)
                 hciProcess.destroy();
        }
    }

    public void run() {
        enableHciScan();
    }
}
