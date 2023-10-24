package com.sociame.app.core.usecases.accounts.application.ports.out;

import com.sociame.app.core.usecases.accounts.domain.RegisterAccountCommand;

public interface RegisterAccountServicePort {

    boolean createUserAccount(RegisterAccountCommand command, String verificationToken, int pin);

}
