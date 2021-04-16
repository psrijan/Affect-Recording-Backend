// created by Srijan Pandey (sp3557@drexel.edu) at 20210305 22:59.
package com.cheney.careu.backend.services.base;
import com.cheney.careu.backend.exception.ServiceException;

import static com.cheney.careu.backend.constants.MessageConstants.*;

public class ServiceFactory {
    /**
     * Used to receive the type of service that is 
     * requested based on tag information.
     * @param tag
     * @param command
     * @return
     */
    public static IService getService (String tag, String command) throws ServiceException{
        switch (tag) {
            case VIDEO_TAG:
                return new VideoService(tag, command);
            case AUDIO_TAG:
                return new AudioService(tag , command);
            case DEPTH_TAG:
                return new DepthService(tag);
            default:
                throw new ServiceException("Service Not Found");
        }
    }
}
