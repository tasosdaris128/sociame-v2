package com.sociame.app.config.web.security;

import com.sociame.app.utils.AuthUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
@Slf4j
public class FilterForJWTInHeader extends OncePerRequestFilter {
    private final JWTAuthenticationProvider jwtAuthenticationProvider;
    private final AuthenticationFactory authenticationFactory = new AuthenticationFactory();

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            Optional.ofNullable(request.getHeader(AUTHORIZATION))
                    .map(token -> token.replaceFirst("(?i)Bearer", ""))
                    .map(String::trim)
                    .flatMap(this::tryCreateAuthentication)
                    .flatMap(this::tryAuthenticate)
                    .ifPresent(SecurityContextHolder.getContext()::setAuthentication);
        } catch (Exception e) {
            log.error("Exception while authenticating in Api Key or JWT Filter", e);
        }
        filterChain.doFilter(request, response);
    }

    private Optional<Authentication> tryCreateAuthentication(String token) {
        return authenticationFactory.fromJWTToken(token);
    }

    private Optional<Authentication> tryAuthenticate(Authentication authentication) {
        return AuthUtils.tryAuthenticate(jwtAuthenticationProvider, authentication);
    }
}
