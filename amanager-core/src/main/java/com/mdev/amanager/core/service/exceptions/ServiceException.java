package com.mdev.amanager.core.service.exceptions;

import java.text.MessageFormat;

/**
 * Created by gmilazzo on 16/10/2018.
 */
public class ServiceException extends Exception {

    public static String nullParam(String paramName) {
        return MessageFormat.format("parameter {0} is null.", paramName);
    }

    public static final String NULL_OBJECT = "null object as parameter.";
    public static final String DETACHED_INSTANCE = "detached instance as parameter.";

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
