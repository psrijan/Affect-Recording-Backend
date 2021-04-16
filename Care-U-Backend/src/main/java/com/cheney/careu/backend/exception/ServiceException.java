package com.cheney.careu.backend.exception;

/**
 * Any Issue with the service must throw this class
 * with appropriate exception.
 */
public class ServiceException extends Exception{

    public ServiceException(String description) {
        super(description);
    }
}
