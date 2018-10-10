package com.mdev.amanager.persistence.domain.model;

import com.mdev.amanager.persistence.domain.enums.PermissionType;
import com.mdev.amanager.persistence.domain.model.base.Identifiable;

import javax.persistence.*;

/**
 * Created by gmilazzo on 01/10/2018.
 */
@Entity
@Table(name = "PERMISSION")
public class Permission implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", length = 64, nullable = false)
    private PermissionType type;

    @Column(name = "DESCRIPTION", length = 256, nullable = false)
    private String description;


    @Override
    public Long getId() {
        return id;
    }

    public PermissionType getType() {
        return type;
    }

    public void setType(PermissionType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Permission)) return false;

        Permission that = (Permission) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (type != that.type) return false;
        return description != null ? description.equals(that.description) : that.description == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
