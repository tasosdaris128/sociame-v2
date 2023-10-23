package com.sociame.app.core.usecases.users.application.ports.out;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserDetailsServicePort {

    Optional<UserDetails> loadUserByUsernameAndPassword(String username, String password);

}
