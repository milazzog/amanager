package com.mdev.amanager.core.service.exceptions;

/**
 * Created by gmilazzo on 24/10/2018.
 */
public class SubscriberFeeServiceException extends ServiceException {

    public SubscriberFeeServiceException() {
        super();
    }

    public SubscriberFeeServiceException(String message) {
        super(message);
    }

    public SubscriberFeeServiceException(Throwable cause) {
        super(cause);
    }

    public SubscriberFeeServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
