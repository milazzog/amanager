package com.mdev.amanager.persistence.domain.enums;


import com.mdev.amanager.persistence.domain.model.base.Labeled;

/**
 * Created by gmilazzo on 25/10/2018.
 */
public enum UM implements Labeled {

    PZ("um.pz"),
    KG("um.kg"),
    LT("um.lt");

    private String labelKey;

    UM(String labelKey) {
        this.labelKey = labelKey;
    }

    public String getLabelKey() {
        return labelKey;
    }
}
