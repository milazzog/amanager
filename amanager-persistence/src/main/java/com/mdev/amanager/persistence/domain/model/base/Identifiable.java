package com.mdev.amanager.persistence.domain.model.base;

import java.util.Objects;

/**
 * Created by gmilazzo on 01/10/2018.
 */
public interface Identifiable {

    Long getId();

    default String getIdString() {
        return Objects.nonNull(getId()) ? String.format("%09d", getId()) : "NULL";
    }

}
