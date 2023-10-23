package com.sociame.app.utils;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.Optional;

public class AuthUtils {
    public static Optional<Authentication> tryAuthenticate(AuthenticationProvider provider, Authentication token) {
        try {
            return Optional.ofNullable(provider.authenticate(token));
        } catch (AuthenticationException authenticationException) {
            return Optional.empty();
        }
    }
}
