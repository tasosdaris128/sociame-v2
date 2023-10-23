package com.sociame.app.config.web.security;

import com.sociame.app.core.usecases.users.application.ports.in.UserDetailsFromJWTokenUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Slf4j
@RequiredArgsConstructor
class JWTAuthenticationProvider extends AbstractJWTAuthenticationProvider {

    private final UserDetailsFromJWTokenUseCase userDetailsFromJWTokenUseCase;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, String jwtToken) throws AuthenticationException {
        if (!userDetailsFromJWTokenUseCase.isValidTokenForUser(userDetails, jwtToken)) {
            throw new BadCredentialsException("Invalid Token");
        }
    }

    @Override
    protected UserDetails retrieveUser(String username, String jwtToken) throws AuthenticationException {
        try {
            return userDetailsFromJWTokenUseCase
                    .extractUserFromLocalToken(jwtToken)
                    .orElseThrow(() -> new UsernameNotFoundException("Could not find user with username " + username));
        } catch (RuntimeException failToFindByTokenError) {
            log.debug("Could not retrieve user {}", username);
            log.error(failToFindByTokenError.getMessage(), failToFindByTokenError);
            throw new UsernameNotFoundException(failToFindByTokenError.getMessage(), failToFindByTokenError);
        }
    }
}
