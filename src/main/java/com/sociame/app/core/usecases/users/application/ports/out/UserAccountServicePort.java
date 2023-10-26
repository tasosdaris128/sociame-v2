package com.sociame.app.core.usecases.users.application.ports.out;

import com.sociame.app.core.usecases.users.domain.User;

import java.util.Optional;

@Deprecated
public interface UserAccountServicePort {

    Optional<User> getUserAccountByUsername(String username);

}
