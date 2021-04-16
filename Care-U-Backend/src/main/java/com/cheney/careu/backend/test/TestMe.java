// created by Srijan Pandey (sp3557@drexel.edu) at 20210305 23:03.
package com.cheney.careu.backend.test;

import java.io.IOException;

public class TestMe {

    public static void main(String[] args ) {
        ProcessBuilder pb = new ProcessBuilder("ffmpeg", "-i", "~/careu/input.mp4", "~/careu/output.mp4");
        try {
            Process p = pb.start();
        } catch (IOException error) {
        }
    }
}
