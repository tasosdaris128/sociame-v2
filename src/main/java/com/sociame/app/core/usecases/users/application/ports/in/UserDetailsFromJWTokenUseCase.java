package com.sociame.app.core.usecases.users.application.ports.in;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserDetailsFromJWTokenUseCase {
    Optional<UserDetails> extractUserFromLocalToken(String jwtToken);

    boolean isValidTokenForUser(UserDetails userDetails, String jwtToken);
}
