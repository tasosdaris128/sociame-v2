package com.sociame.app.core.usecases.accounts.application.ports.in;

import com.sociame.app.core.usecases.accounts.domain.SetupPasswordCommand;

public interface SetupPasswordUseCase {

    boolean handleCommand(SetupPasswordCommand command);

}
