// created by Srijan Pandey (sp3557@drexel.edu) at 20210305 23:05.
package com.cheney.careu.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DataMessage {
    private long messageId;
    private long userId;
    private Date recordedDate; // person clicks on button
    private Date sentdate; //
    private Date receivedDate;
    private Activity activity;
    private Emotion emotion;
}

