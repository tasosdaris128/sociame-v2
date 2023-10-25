package com.sociame.app.core.usecases.accounts.application.ports.out;

import com.sociame.app.core.usecases.accounts.domain.Account;
import com.sociame.app.core.usecases.accounts.domain.UpgradeAccountCommand;

import java.util.Optional;

public interface UpgradeAccountServicePort {

    Optional<Account> getCurrentAccount(String username);
    Optional<Account> upgradeAccount(UpgradeAccountCommand command);

}
