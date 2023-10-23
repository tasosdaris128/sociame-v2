package com.sociame.app.config.web.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JWTAuthentication extends UsernamePasswordAuthenticationToken {

    public JWTAuthentication(String token) throws JWTDecodeException {
        super(JWT.decode(token).getSubject(), token);
    }

    private JWTAuthentication(
            Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public static JWTAuthentication authenticated(
            Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        return new JWTAuthentication(principal, credentials, authorities);
    }
}
