package com.sociame.app.core.usecases.users.domain;

import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Value
public class UserDetailsImpl implements UserDetails {

    String username;
    String password;

    public UserDetailsImpl(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static UserDetailsImpl enabledForUsernamePassword(
            String username, String password) {
        return new UserDetailsImpl(username, password);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
