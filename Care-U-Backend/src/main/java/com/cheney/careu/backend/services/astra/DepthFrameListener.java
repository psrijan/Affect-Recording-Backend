// Port of the C++ demo from the Astra SDK Overview to Java.
package com.cheney.careu.backend.services.astra;

import com.orbbec.astra.*;

/**
 * Author: Kei Kebreau | Cheyney University
 * Modified By: Srijan Pandey (sp3557@drexel.edu) | Drexel Unviersity
 */
public class DepthFrameListener implements StreamReader.FrameListener {
    /** Maximum number of frames to record */
    private int maxFrameCount;

    /** Current number of frames recorded */
    private int frameCount;

    /** True when frameCount is greater than maxFrameCount. */
    private boolean doneRecording;

    /**
     * Initialize frame count.
     */
    public DepthFrameListener(DepthStream depthStream, int maxFrameCount) {
        this.maxFrameCount = maxFrameCount;
    }

    public void onFrameReady(StreamReader streamReader, ReaderFrame readerFrame) {
        DepthFrame depthFrame = DepthFrame.get(readerFrame);
        if (depthFrame.isValid() && frameCount <= maxFrameCount) {
            System.out.println("Depth frame " + depthFrame.getFrameIndex());
            ++frameCount;
        } else if (!doneRecording && frameCount > maxFrameCount) {
            doneRecording = true;
        }
    }

    /** Return true when the listener is no longer recording new frames and false otherwise. */
    public boolean isDoneRecording() {
        return doneRecording;
    }
}
