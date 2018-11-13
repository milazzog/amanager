package com.mdev.amanager.persistence.domain.model.base;

import com.mdev.amanager.persistence.domain.model.CreditNote;

import java.util.Objects;

/**
 * Created by gmilazzo on 13/11/2018.
 */
public abstract class DomainObject implements Identifiable {

    @Override
    public int hashCode() {
        int result = this.getClass().getSimpleName().hashCode();
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;
        if (Objects.isNull(obj)) return false;

        if (!(obj.getClass().isAssignableFrom(this.getClass()))) return false;

        return this.hashCode() == obj.hashCode();
    }
}
