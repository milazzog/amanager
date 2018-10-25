package com.mdev.amanager.persistence.domain.repository.exceptions;

/**
 * Created by gmilazzo on 01/10/2018.
 */
public class EntityNotFoundException extends EntityPersistenceException {

    public EntityNotFoundException(Throwable cause) {
        super(cause);
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
