package com.mdev.amanager.persistence.domain.repository.params.base;

import java.util.Objects;

/**
 * Created by gmilazzo on 02/11/2018.
 */
@ValueMatcher
public class NumberMatcher<T extends Number> {

    private T from;
    private T to;

    public T getFrom() {
        return from;
    }

    public void setFrom(T from) {
        this.from = from;
    }

    public T getTo() {
        return to;
    }

    public void setTo(T to) {
        this.to = to;
    }

    public static boolean setted(NumberMatcher o) {
        return Objects.nonNull(o.to) || Objects.nonNull(o.from);
    }
}
