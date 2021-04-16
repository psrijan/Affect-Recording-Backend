// Port of the C++ demo from the Astra SDK Overview to Java.
package com.cheney.careu.backend.services.astra;
import com.orbbec.astra.*;

import java.nio.ByteBuffer;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;

/**
 * Author: Kei Kebreau | Cheyney University
 * Modified By: Srijan Pandey (sp3557@drexel.edu) | Drexel University
 */
public class ColorFrameListener implements StreamReader.FrameListener {
    /** Video file output object */
    private VideoWriter videoOut;

    /** Image width */
    private int width;
    /** Image height */
    private int height;

    /** Maximum number of frames to record */
    private int maxFrameCount;

    /** Current number of frames recorded */
    private int frameCount;

    /** True when frameCount is greater than maxFrameCount. */
    private boolean doneRecording;

    /**
     * Initialize video file output and frame count.
     */
    public ColorFrameListener(ColorStream colorStream, int maxFrameCount, String fileName) {
        ImageStreamMode ism = colorStream.getMode();
        width = ism.getWidth();
        height = ism.getHeight();

        // Define the codec and create VideoWriter object.
        // The output is stored in "output.mp4" file.
        videoOut = new VideoWriter(fileName, VideoWriter.fourcc('x', '2', '6', '4'),
                30, new Size(width, height));

        // Check if the video writer opened successfully.
        if (!videoOut.isOpened()) {
            System.err.println("Unable to open video file");
            System.exit(1);
        }

        this.maxFrameCount = maxFrameCount;
    }

    public void onFrameReady(StreamReader streamReader, ReaderFrame readerFrame) {
        ColorFrame colorFrame = ColorFrame.get(readerFrame);
        if (!videoOut.isOpened()) {
            System.exit(1);
        }
        if (colorFrame.isValid() && frameCount <= maxFrameCount) {
            System.out.println("Color frame " + colorFrame.getFrameIndex());
            // Create a new matrix with the given dimensions so that each
            // element is an 8-bit unsigned element with three channels.
            Mat image = new Mat(width, height, CvType.CV_8UC3, colorFrame.getByteBuffer());
            Imgproc.cvtColor(image, image, Imgproc.COLOR_BGR2RGB);
            videoOut.write(image);
            ++frameCount;
        } else if (!doneRecording && frameCount > maxFrameCount) {
            doneRecording = true;
            videoOut.release();
        }
    }

    /** Return true when the listener is no longer recording new frames and false otherwise. */
    public boolean isDoneRecording() {
        return doneRecording;
    }
}
