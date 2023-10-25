package com.sociame.app.core.usecases.accounts.application.ports.in;

import com.sociame.app.core.usecases.accounts.domain.FollowAccountCommand;

public interface FollowAccountUseCase {

    boolean handleCommand(FollowAccountCommand command);

}
