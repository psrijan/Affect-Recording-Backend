package com.cheney.careu.backend.socket;


import com.cheney.careu.backend.core.DataProcessingModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Author: Srijan Pandey (sp3557@drexel.edu)
 * This File is the Server side main-apps Socket connection code.
 * This receives the Ble message from the low level java wrapper for
 * HCI tool using socket connection. This
 */
public class BackendSocket {
    private Socket socket;
    private DataProcessingModule dataProcessingModule;

    private Logger logger = LoggerFactory.getLogger(BackendSocket.class);

    public BackendSocket (DataProcessingModule dataProcessingModule) {
        this.dataProcessingModule = dataProcessingModule;
    }

    /**
     *  The server socket connection has a link to the java-low-level-wrapper
     *  which then receives BLE message from the wrapper and sends it along to
     *  the DataProcessingUnit
     */
    public void enableBackendSocket() {
        try {
            logger.debug("Starting Backend Socket...");
            ServerSocket serverSocket = new ServerSocket(5001);
            socket = serverSocket.accept();
            InputStream is = socket.getInputStream();
            DataInputStream dis = new DataInputStream(is);
            String data = "";
            boolean skip = false;
            do {
                if (dis.available() <= 0) {
                    logger.debug("No data in stream currently...");
                    Thread.sleep(1000);
                    skip = true;
                    continue;
                }
                skip = false;
                data = dis.readUTF();
                if (data.length() > 0)
                    dataProcessingModule.processData(data);
            } while (data.length() > 0 || skip);
        } catch (IOException ex ) {
            logger.error("Error" , ex);
        } catch (InterruptedException ex) {
            logger.error("Error In enable Backend: " , ex);
        }
    }

}
