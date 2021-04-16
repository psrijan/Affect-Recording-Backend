// created by Srijan Pandey (sp3557@drexel.edu) at 20210305 23:00.
package com.cheney.careu.backend.utilities;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.cheney.careu.backend.constants.MessageConstants.DEPTH_TAG;

/**
 * Author: Srijan Pandey (sp3557@drexel.edu)
 * Utility function to help create sensible file names based on TAG
 * and DATE information
 */
public class FileNameUtil {

    private FileNameUtil() {
    }

    public static String formatCommand(String command, String tag, String folder) {
        DateFormat format = new SimpleDateFormat("dd-MM-yyy_hh-mm-ss");
        if (tag.equals(DEPTH_TAG))
            return command;
        return command.replace("<file_name>", folder + tag + "_" + format.format(new Date()));
    }

    public static String getDepthFileName(String tag, String folder) {
        DateFormat format = new SimpleDateFormat("dd-MM-yyy_hh-mm-ss");
        return folder + tag + "_" + format.format(new Date());
    }
}
