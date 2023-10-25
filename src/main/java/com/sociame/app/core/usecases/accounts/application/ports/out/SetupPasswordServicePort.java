package com.sociame.app.core.usecases.accounts.application.ports.out;

import com.sociame.app.core.usecases.accounts.domain.Account;
import com.sociame.app.core.usecases.accounts.domain.SetupPasswordCommand;

import java.util.Optional;

public interface SetupPasswordServicePort {

    Optional<Account> setupPassword(SetupPasswordCommand command);

}
