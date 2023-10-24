package com.sociame.app.core.usecases.users.domain;

import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.List;

@Value
public class User implements UserDetails {
    UserId id;
    String password;
    String username;
    LocalDateTime createdAt;
    List<String> authorities;
    boolean enabled;
    boolean expired;
    boolean locked;
    boolean credentialsExpired;

    public List<GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(authorities.toArray(new String[0]));
    }

    @Override
    public boolean isAccountNonExpired() {
        return !expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !credentialsExpired;
    }

    public boolean containsRole(String role) {
        return this.authorities.contains(role);
    }
}
