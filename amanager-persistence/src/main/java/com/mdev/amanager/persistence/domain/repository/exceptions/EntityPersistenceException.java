package com.mdev.amanager.persistence.domain.repository.exceptions;

/**
 * Created by gmilazzo on 23/10/2018.
 */
public class EntityPersistenceException extends Exception {

    public EntityPersistenceException(Throwable cause) {
        super(cause);
    }

    public EntityPersistenceException(String message) {
        super(message);
    }

    public EntityPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
