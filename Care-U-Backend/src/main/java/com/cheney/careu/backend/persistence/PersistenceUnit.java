// created by Srijan Pandey (sp3557@drexel.edu) at 20210305 22:56.
package com.cheney.careu.backend.persistence;

import com.cheney.careu.backend.dto.Activity;
import com.cheney.careu.backend.dto.Emotion;
import com.cheney.careu.backend.dto.DataMessage;
import com.cheney.careu.backend.utilities.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Persistence unit that makes a file and stores the
 * user data message in the file.
 */
public class PersistenceUnit {

    public static final String FILE_HEADER = "careu_data_";
    public static final String BASE_URL = "/careu";
    private final Logger logger = LoggerFactory.getLogger(PersistenceUnit.class);

    public void saveToFile(DataMessage messageDTO) {
        String persistString = getPersistString(messageDTO);
        File file;
        FileOutputStream fos;
        String filename = getFileName();

        try {
            file = new File(filename);
            if (!file.exists()) {
                logger.debug("Creating New File {}", filename);
                file.createNewFile();
            } else {
                logger.debug("File Exists");
            }
            fos = new FileOutputStream(file, true);
            fos.write(persistString.getBytes());
            fos.close();
        } catch (IOException ex) {
            logger.error("Error: " , ex);
        }
        logger.info("Completed Save...");
    }

    private String getFileName() {
        DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_hh");
        String formattedDate = dateFormat.format(new Date());
        String dir = Config.getDataDirectory();
        return dir + FILE_HEADER + formattedDate;
    }

    private String getPersistString(DataMessage messageDTO) {
        StringBuilder stringBuilder = new StringBuilder();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");
        return stringBuilder.append(messageDTO.getUserId()).append(",")
                .append(messageDTO.getMessageId()).append(",")
                .append(messageDTO.getActivity()).append(",")
                .append(simpleDateFormat.format(messageDTO.getSentdate())).append(",")
                .append(messageDTO.getEmotion()).append(",")
                .append("\n")
                .toString();
    }

    public static void main(String[] args) {
        PersistenceUnit saveCsvToFile = new PersistenceUnit();
        DataMessage messageDTO = new DataMessage();
        messageDTO.setActivity(Activity.Eating);
        messageDTO.setEmotion(Emotion.HAPPY);
        messageDTO.setRecordedDate(new Date());
        messageDTO.setUserId(1);
        messageDTO.setMessageId(1);
        saveCsvToFile.saveToFile(messageDTO);
    }
}
