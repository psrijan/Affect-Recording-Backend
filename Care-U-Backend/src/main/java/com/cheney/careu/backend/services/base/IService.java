// created by Srijan Pandey (sp3557@drexel.edu) at 20210305 23:02.
package com.cheney.careu.backend.services.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface IService {
    Logger logger = LoggerFactory.getLogger(IService.class);
    void enable();
    void disable();

}
