package com.sociame.app.config.web.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

abstract class AbstractJWTAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Override
    protected void additionalAuthenticationChecks(
            UserDetails userDetails, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        additionalAuthenticationChecks(((UserDetails) userDetails), ((String) authentication.getCredentials()));
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        return retrieveUser(username, ((String) authentication.getCredentials()));
    }

    protected abstract void additionalAuthenticationChecks(UserDetails userDetails, String jwtToken)
            throws AuthenticationException;

    protected abstract UserDetails retrieveUser(String username, String jwtToken) throws AuthenticationException;

    @Override
    protected Authentication createSuccessAuthentication(
            Object principal, Authentication authentication, UserDetails user) {
        // taken from AbstractUserDetailsAuthenticationProvider
        // Ensure we return the original credentials the user supplied,
        // so subsequent attempts are successful even with encoded passwords.
        // Also ensure we return the original getDetails(), so that future
        // authentication events after cache expiry contain the details

        // principal -> User class
        // authentication JWT authentication
        // user details the user details from user
        JWTAuthentication result =
                JWTAuthentication.authenticated(principal, authentication.getCredentials(), user.getAuthorities());
        result.setDetails(authentication.getDetails());
        this.logger.debug("Authenticated user");
        return result;
    }
}
