package com.mdev.amanager.core.datasource.validation;

/**
 * Created by gmilazzo on 07/10/2018.
 */
public class MissingRequiredFieldException extends Exception {

    public MissingRequiredFieldException() {
        super();
    }

    public MissingRequiredFieldException(String message) {
        super(message);
    }

    public MissingRequiredFieldException(Throwable cause) {
        super(cause);
    }

    public MissingRequiredFieldException(String message, Throwable cause) {
        super(message, cause);
    }
}
