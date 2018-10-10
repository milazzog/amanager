package com.mdev.amanager.persistence.domain.enums;

import com.mdev.amanager.persistence.domain.model.base.Labeled;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.GeneratedValue;
import java.util.stream.Stream;

/**
 * Created by gmilazzo on 06/10/2018.
 */
public enum Gender implements Labeled {

    M("gender.male"),
    F("gender.female");

    String labelKey;

    Gender(String labelKey) {
        this.labelKey = labelKey;
    }

    @Override
    public String getLabelKey() {
        return labelKey;
    }

    public static Gender fromName(String name){
        return Stream
                .of(Gender.values())
                .filter(v -> StringUtils.equalsIgnoreCase(v.name(), name))
                .findFirst()
                .orElse(null);
    }
}
