// created by Srijan Pandey (sp3557@drexel.edu) at 20210305 23:05.
package com.cheney.careu.backend.dto;

public enum ServiceType {
    ALL('A'),
    VIDEO_SERVICE('V'),
    AUDIO_SERVICE('A'),
    DEPTH_SERVICE('D'),
    DEFAULT('U');

    private final Character serviceDescriptor;

    ServiceType(Character serviceDescriptor) {
        this.serviceDescriptor = serviceDescriptor;
    }

    public static ServiceType valueOf(Character serviceDescriptor) {
        for (ServiceType e : values()) {
            if (e.serviceDescriptor.equals(serviceDescriptor))
                return e;
        }
        return ServiceType.DEFAULT;
    }

    public Character getServiceDescriptor() {
        return serviceDescriptor;
    }

}