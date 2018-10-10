package com.mdev.amanager.persistence.domain.enums;

import com.mdev.amanager.persistence.domain.model.base.Labeled;

/**
 * Created by gmilazzo on 02/10/2018.
 */
public enum IdentityDocumentType implements Labeled {

    IDENTITY_CARD("identity.document.type.ic"),
    DRIVING_LICENCE("identity.document.type.dl");

    private String labelKey;

    IdentityDocumentType(String labelKey) {
        this.labelKey = labelKey;
    }

    @Override
    public String getLabelKey() {
        return labelKey;
    }
}
