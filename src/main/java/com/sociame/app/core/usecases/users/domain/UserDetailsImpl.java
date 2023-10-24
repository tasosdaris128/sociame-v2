package com.sociame.app.core.usecases.users.domain;

import jakarta.validation.OverridesAttribute;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Value
public class UserDetailsImpl implements UserDetails {

    String username;
    String password;
    List<? extends GrantedAuthority> authorities;
    boolean enabled;
    boolean expired;
    boolean locked;
    boolean credentialsExpired;

    public UserDetailsImpl(
            String username,
            String password,
            List<? extends GrantedAuthority> authorities,
            boolean enabled,
            boolean expired,
            boolean locked,
            boolean credentialsExpired
    ) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.enabled = enabled;
        this.expired = expired;
        this.locked = locked;
        this.credentialsExpired = credentialsExpired;
    }

    public static UserDetailsImpl enabledForUsernamePasswordAndAuthorities(
            String username, String password, String... authorities) {
        List<GrantedAuthority> authorityList =
                (authorities == null) ? List.of() : AuthorityUtils.createAuthorityList(authorities);
        return new UserDetailsImpl(
                username,
                password,
                authorityList,
                true,
                false,
                false,
                false
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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

}
