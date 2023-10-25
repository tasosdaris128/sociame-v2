package com.sociame.app.core.usecases.accounts.application.ports.out;

import com.sociame.app.core.usecases.accounts.domain.SetupPasswordCommand;

public interface SetupPasswordServicePort {

    String setupPassword(SetupPasswordCommand command);

}
