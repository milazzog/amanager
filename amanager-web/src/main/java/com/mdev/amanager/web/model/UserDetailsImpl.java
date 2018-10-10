package com.mdev.amanager.web.model;

import com.mdev.amanager.persistence.domain.model.Permission;
import com.mdev.amanager.persistence.domain.model.User;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by gmilazzo on 01/10/2018.
 */
public class UserDetailsImpl implements UserDetails {

    public static final String ROLE_PREFIX = "ROLE_";

    private User user;
    private List<GrantedAuthority> authorities;

    public UserDetailsImpl(User user) {
        this.user = user;
        initAuthorities(user.getPermissions());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

    public User getUser(){
        return user;
    }

    private void initAuthorities(Set<Permission> permissions) {

        if (CollectionUtils.isNotEmpty(permissions)) {

            this.authorities = permissions
                    .stream()
                    .map(p -> (GrantedAuthority) () -> String.format("%s%s", ROLE_PREFIX, p.getType().name()))
                    .collect(Collectors.toList());
        } else {
            this.authorities = new ArrayList<>();
        }
    }
}
