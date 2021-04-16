package com.cheyney.careu.wrapper;

import java.io.*;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.SocketException;

import java.lang.Thread;

public class WrapperMain {

    private DataOutputStream socketOutputStream; // output stream to send data to Care-U-Backend
    private HciService hciService;
    private BtmonService btmonService;

    /**
     * Thread that enables Hci service. This needs to be done on a separate thread because the
     * btmon code needs to read data off of the btmon service and is a endless loop that reads
     * data from an input stream.
     */
    public void enableBluetoothService() {
        hciService = new HciService();
        btmonService = new BtmonService();
        btmonService.setOutputStream(socketOutputStream); 
        Thread hciThread = new Thread(hciService);
        hciThread.start();

        Thread btmonThread = new Thread(btmonService);
        btmonThread.start();
    }


    /**
     * This is the socket connection code that connects to the main backend code
     * and sends data to the care u backend project.
     * @param address
     * @param port
     */
    public void createSocket(String address, int port ) {
        Socket socket =null;
        try {
            socket = new Socket(address, port);
            OutputStream os = socket.getOutputStream();
            socketOutputStream = new DataOutputStream(os);
            socketOutputStream.writeUTF("Test Message from Client");
        } catch (IOException ex ) {
            ex.printStackTrace();
        }
    }


    /**
     * Integration Test Code: This code is used to send mock messages to the 
     * Care-U-Backend to check all servers are running fine.
     */
    public void sendMockMessages() {
        String[] sendMessage = MockMessages.overallMessage();
        try {
            for (int i = 0; i < sendMessage.length; i++) {
                System.out.println("Sending " + sendMessage[i]);
                socketOutputStream.writeUTF(sendMessage[i]);
                Thread.sleep(10000);
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
            return;
        } catch (IOException ex ) {
            ex.printStackTrace();
        } catch (InterruptedException ex ) {
            ex.printStackTrace();
        }
    }


    public static void main (String[] args ) {
        WrapperMain wrapperMain = new WrapperMain();
        boolean isLive= true;

        if (args.length >= 1) {
            isLive= !args[0].equalsIgnoreCase("test");
        }

        wrapperMain.createSocket("localhost", 5001);
        if (isLive){
            wrapperMain.enableBluetoothService();
        } else {
            System.out.println("Sending Mock Messages To Backend...");
            wrapperMain.sendMockMessages();
        }
            
    }
}
