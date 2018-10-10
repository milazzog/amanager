package com.mdev.amanager.persistence.domain.model.base;

/**
 * Created by gmilazzo on 01/10/2018.
 */
public interface Identifiable {

    Long getId();

    default String getIdString(){
        return String.format("%09d", getId());
    }
}
