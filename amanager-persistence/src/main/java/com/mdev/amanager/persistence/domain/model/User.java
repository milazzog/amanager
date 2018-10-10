package com.mdev.amanager.persistence.domain.model;

import com.mdev.amanager.persistence.domain.model.base.Identifiable;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by gmilazzo on 01/10/2018.
 */
@Entity
@Table(name = "USER", uniqueConstraints = @UniqueConstraint(name = "USER_USERNAME_UNIQUE", columnNames = "USERNAME"))
@NamedQueries({
        @NamedQuery(name = "user.find.by.username", query = "from User u where u.username = :username")
})
public class User implements Identifiable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
    @Column(name = "USERNAME", length = 64, nullable = false)
    private String username;
    @Column(name = "PASSWORD", length = 64, nullable = false)
    private String password;
    @Column(name = "ENABLED", nullable = false)
    private boolean enabled;
    @Column(name = "ADMIN", nullable = false)
    private boolean admin;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "PERMISSIONS_USERS",
            joinColumns = {@JoinColumn(name = "PERMISSION", nullable = false, foreignKey = @ForeignKey(name = "FK_PERMISSIONS_USERS_PERMISSION"))},
            inverseJoinColumns = {@JoinColumn(name = "USER", nullable = false, foreignKey = @ForeignKey(name = "FK_PERMISSIONS_USERS_USER"))})
    private Set<Permission> permissions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }
}
