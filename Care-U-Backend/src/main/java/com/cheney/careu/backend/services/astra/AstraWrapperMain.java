// Port of pieces of the C++ demo from the Astra SDK Overview to Java.
package com.cheney.careu.backend.services.astra;

import com.cheney.careu.backend.utilities.FileNameUtil;
import com.orbbec.astra.*;

import org.opencv.core.Core;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Author: Kei Kebreau | Cheyney University
 * Modified By: Srijan Pandey (sp3557@drexel.edu) | Drexel University
 * Code Written by Kei (email) and made compatible to Care-U-Backend by Srijan Pandey
 */
public class AstraWrapperMain {
    // Max image and time resolutions for depth and RGB are set according to
    // https://orbbec3d.com/product-persee/
    private static final int MAX_FPS = 30;

    private static final int DEPTH_MAX_WIDTH = 640;
    private static final int DEPTH_MAX_HEIGHT = 480;

    private static final int RGB_MAX_WIDTH = 1280;
    private static final int RGB_MAX_HEIGHT = 720;
    private DepthStream depthStream;
    private ColorStream colorStream;
    private StreamReader reader;
    private StreamSet streamSet;
    private String fileName;
    private Logger logger = LoggerFactory.getLogger(AstraWrapperMain.class);

    public AstraWrapperMain(String tag, String baseFile) {
        // Load the native libraries.
        logger.debug("Initializing Astra Wrapper Main...");
        fileName = FileNameUtil.getDepthFileName(tag, baseFile);
        System.loadLibrary("astra");
        System.loadLibrary("astra_core");
        System.loadLibrary("astra_jni");
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        logger.debug("Astra Wrapper A");
        // Initialize the device
        Astra.initialize();

        logger.debug("Astra Wrapper B");
        // Read from a low-level DepthStream.
        StreamSet streamSet = StreamSet.open();

        logger.debug("Astra Wrapper B");
        reader = streamSet.createReader();

        logger.debug("Astra Wrapper C");
        depthStream = DepthStream.get(reader);

        logger.debug("Astra Wrapper D");
        colorStream = ColorStream.get(reader);

        logger.debug("Astra Wrapper E");
        // Mode: ID, width, height, pixel format, frames per second
        // (ID can be 0 for any mode, since it seems to be calculated based on
        // the other arguments?)
        depthStream.setMode(new ImageStreamMode(0, DEPTH_MAX_WIDTH, DEPTH_MAX_HEIGHT, PixelFormat.DEPTH_MM.getCode(), MAX_FPS));

        logger.debug("Astra Wrapper F");
        colorStream.setMode(new ImageStreamMode(0, RGB_MAX_WIDTH, RGB_MAX_HEIGHT, PixelFormat.RGB888.getCode(), MAX_FPS));

        logger.debug("Astra Wrapper G");
        System.out.println("Depth Stream Mode: " + depthStream.getMode());
        System.out.println("Color Stream Mode: " + colorStream.getMode());
    }

    public void startRecordingDepth() {
        logger.debug("Starting Depth Recording...");
        DepthFrameListener depthListener = new DepthFrameListener(depthStream, 60); ColorFrameListener colorListener = new ColorFrameListener(colorStream, 60, fileName);
        reader.addFrameListener(depthListener);
        reader.addFrameListener(colorListener);

        depthStream.start();
        colorStream.start();

        // Check for new events for a while.
        while (!(depthListener.isDoneRecording() && colorListener.isDoneRecording())) {
            Astra.update();
        }
        try {
            streamSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Astra.terminate();
        }
    }

    //@todo
    public void disable() {
        depthStream.stop();
        colorStream.stop();
    }
}
