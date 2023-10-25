package com.sociame.app.core.usecases.accounts.application.ports.out;

import com.sociame.app.core.usecases.accounts.domain.Account;
import com.sociame.app.core.usecases.accounts.domain.ChangePlanCommand;

import java.util.Optional;

public interface ChangePlanServicePort {

    Optional<Account> changePlan(ChangePlanCommand command);

}
