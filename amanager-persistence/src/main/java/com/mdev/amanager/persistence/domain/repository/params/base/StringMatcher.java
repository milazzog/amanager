package com.mdev.amanager.persistence.domain.repository.params.base;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * Created by gmilazzo on 05/10/2018.
 */
@ValueMatcher
public class StringMatcher{

    private String value;
    private MatchingMode mode;

    public StringMatcher() {
        this.mode = MatchingMode.ANYWHERE;
    }

    public StringMatcher(String value, MatchingMode mode) {
        this.value = value;
        this.mode = mode;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public MatchingMode getMode() {
        return mode;
    }

    public void setMode(MatchingMode mode) {
        this.mode = mode;
    }

    public static boolean setted(StringMatcher m){
        return Objects.nonNull(m) && Objects.nonNull(m.getMode()) && StringUtils.isNotBlank(m.getValue());
    }
}
