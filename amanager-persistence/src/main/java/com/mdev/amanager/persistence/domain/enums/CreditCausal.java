package com.mdev.amanager.persistence.domain.enums;

import com.mdev.amanager.persistence.domain.model.base.Labeled;

/**
 * Created by gmilazzo on 21/10/2018.
 */
public enum CreditCausal implements Labeled {

    SUBSCRIBER_FEE("credit.causal.subscriber.fee");

    private String labelKey;

    CreditCausal(String labelKey) {
        this.labelKey = labelKey;
    }

    @Override
    public String getLabelKey() {
        return labelKey;
    }
}
