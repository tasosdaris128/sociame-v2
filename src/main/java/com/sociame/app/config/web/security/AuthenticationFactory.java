package com.sociame.app.config.web.security;

import com.auth0.jwt.exceptions.JWTDecodeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;

import java.util.Optional;

@Slf4j
public class AuthenticationFactory {

    public Optional<Authentication> fromJWTToken(String token) {
        try {
            return Optional.of(new JWTAuthentication(token));
        } catch (JWTDecodeException decodeException) {
            log.debug("Could not decode token ", decodeException);
            log.error(decodeException.getMessage(), decodeException);
            return Optional.empty();
        }
    }

}
