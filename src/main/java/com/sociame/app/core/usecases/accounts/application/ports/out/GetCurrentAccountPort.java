package com.sociame.app.core.usecases.accounts.application.ports.out;

import com.sociame.app.core.usecases.accounts.domain.Account;

import java.util.Optional;

public interface GetCurrentAccountPort {

    Optional<Account> getCurrentAccount(String username);

}
