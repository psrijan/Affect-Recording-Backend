// created by Srijan Pandey (sp3557@drexel.edu) at 20210305 23:05.
package com.cheney.careu.backend.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class CommandMessage {
    private ServiceType serviceType;
    private boolean isEnableRequest;
}
