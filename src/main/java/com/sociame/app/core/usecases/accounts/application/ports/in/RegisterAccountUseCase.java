package com.sociame.app.core.usecases.accounts.application.ports.in;

import com.sociame.app.core.usecases.accounts.domain.RegisterAccountCommand;

public interface RegisterAccountUseCase {

    boolean handleCommand(RegisterAccountCommand command);

}
