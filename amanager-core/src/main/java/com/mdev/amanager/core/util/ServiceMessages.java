package com.mdev.amanager.core.util;

import com.mdev.amanager.persistence.domain.model.base.Identifiable;

import java.text.MessageFormat;

/**
 * Created by gmilazzo on 06/11/2018.
 */
public class ServiceMessages {

    private static final String ERROR_ON_CREATE = "error while creating {0}: ";
    private static final String CREATE_SUCCESS = "{0} successfully created with id {1}.";
    private static final String ERROR_ON_EDIT = "error while editing {0} with id {1}: ";
    private static final String EDIT_SUCCESS = "{0} with id {1} successfully edited.";
    private static final String CHANGES_DETECTED_ON_READ_ONLY_ENTITY = "changes detected on read only entity of type {0} with id {1}, creating new.";
    private static final String NO_CHANGES_DETECTED_ON_READ_ONLY_ENTITY = "no changes detected on read only entity of type {0} with id {1}, returning old instance.";

    public static String errorOnCreate(Class<? extends Identifiable> type) {

        return MessageFormat.format(ERROR_ON_CREATE, type.getSimpleName());
    }

    public static String createSuccess(Identifiable obj) {

        return MessageFormat.format(CREATE_SUCCESS, obj.getClass().getSimpleName(), obj.getIdString());
    }

    public static String errorOnEdit(Identifiable obj) {

        return MessageFormat.format(ERROR_ON_EDIT, obj.getClass().getSimpleName(), obj.getIdString());
    }

    public static String editSuccess(Identifiable obj) {

        return MessageFormat.format(EDIT_SUCCESS, obj.getClass().getSimpleName(), obj.getIdString());
    }

    public static String changesDetectedOnReadOnlyEntity(Identifiable obj) {
        return MessageFormat.format(CHANGES_DETECTED_ON_READ_ONLY_ENTITY, obj.getClass().getSimpleName(), obj.getIdString());
    }

    public static String noChangesDetectedOnReadOnlyEntity(Identifiable obj) {
        return MessageFormat.format(NO_CHANGES_DETECTED_ON_READ_ONLY_ENTITY, obj.getClass().getSimpleName(), obj.getIdString());
    }
}
