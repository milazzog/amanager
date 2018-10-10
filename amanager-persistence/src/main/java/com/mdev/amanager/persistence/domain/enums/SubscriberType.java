package com.mdev.amanager.persistence.domain.enums;

import com.mdev.amanager.persistence.domain.model.base.Labeled;

/**
 * Created by gmilazzo on 02/10/2018.
 */
public enum SubscriberType implements Labeled{

    SYMPATHIZER("subscriber.type.sympathizer"),
    SUPPORTER("subscriber.type.supporter");

    private String labelKey;

    SubscriberType(String labelKey) {
        this.labelKey = labelKey;
    }

    @Override
    public String getLabelKey() {
        return labelKey;
    }
}
