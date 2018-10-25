package com.mdev.amanager.persistence.domain.repository.exceptions;

/**
 * Created by gmilazzo on 23/10/2018.
 */
public class MultipleEntityFoundException extends EntityPersistenceException {

    public MultipleEntityFoundException(Throwable cause) {
        super(cause);
    }

    public MultipleEntityFoundException(String message) {
        super(message);
    }

    public MultipleEntityFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
