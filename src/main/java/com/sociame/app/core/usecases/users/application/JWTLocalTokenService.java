package com.sociame.app.core.usecases.users.application;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sociame.app.core.usecases.users.application.ports.in.UserDetailsFromJWTokenUseCase;
import com.sociame.app.core.usecases.users.domain.JWTSettings;
import com.sociame.app.core.usecases.users.domain.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@RequiredArgsConstructor
@Slf4j
public class JWTLocalTokenService implements UserDetailsFromJWTokenUseCase {
    private final JWTSettings settings;
    private final Clock clock;

    String createToken(Authentication authentication) {
        final ZonedDateTime nowAtZoneDataTime = ZonedDateTime.now(clock);
        Date now = Date.from(nowAtZoneDataTime.toInstant());
        Date nowAfterMinutes = Date.from(
                nowAtZoneDataTime.plusMinutes(settings.expirationInMinutes()).toInstant());
        return JWT.create()
                .withSubject(authentication.getName())
                .withIssuer(settings.issuer())
                .withIssuedAt(now)
                .withNotBefore(now)
                .withExpiresAt(nowAfterMinutes)
                .withArrayClaim(
                        "authorities", // Although we are not gonna utilize them
                        authentication.getAuthorities().stream()
                                .map(String::valueOf)
                                .toArray(String[]::new))
                .sign(HMAC512(settings.secret().getBytes(StandardCharsets.UTF_8)));
    }

    @Override
    public Optional<UserDetails> extractUserFromLocalToken(String jwtToken) {
        try {
            if (!isValidLocalToken(jwtToken)) {
                return Optional.empty();
            }
            final DecodedJWT decodedJWT = JWT.decode(jwtToken);
            UserDetails userDetails = UserDetailsImpl.enabledForUsernamePasswordAndAuthorities(
                    decodedJWT.getSubject(),
                    jwtToken,
                    decodedJWT.getClaim("authorities").as(String[].class)
            );
            return Optional.of(userDetails);
        } catch (JWTDecodeException decodeException) {
            log.debug("JWT Decoding failure reason", decodeException);
        }
        return Optional.empty();
    }

    private boolean isValidLocalToken(String jwtToken) {
        try {
            JWT.require(HMAC512(settings.secret().getBytes(StandardCharsets.UTF_8)))
                    .withIssuer(settings.issuer())
                    .build()
                    .verify(jwtToken);
        } catch (JWTVerificationException verificationException) {
            log.debug("JWT Verification failure reason", verificationException);
            return false;
        }
        return true;
    }

    @Override
    public boolean isValidTokenForUser(UserDetails userDetails, String jwtToken) {
        try {
            JWT.require(HMAC512(settings.secret().getBytes(StandardCharsets.UTF_8)))
                    .withIssuer(settings.issuer())
                    .withSubject(userDetails.getUsername())
                    .build()
                    .verify(jwtToken);
        } catch (JWTVerificationException verificationException) {
            log.debug("JWT Verification failure reason", verificationException);
            return false;
        }
        return true;
    }
}
