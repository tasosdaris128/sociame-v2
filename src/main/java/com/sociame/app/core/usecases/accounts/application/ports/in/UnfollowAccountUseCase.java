package com.sociame.app.core.usecases.accounts.application.ports.in;

import com.sociame.app.core.usecases.accounts.domain.UnfollowAccountCommand;

public interface UnfollowAccountUseCase {

    boolean handleCommand(UnfollowAccountCommand command);

}
