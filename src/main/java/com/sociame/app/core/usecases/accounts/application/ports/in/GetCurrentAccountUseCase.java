package com.sociame.app.core.usecases.accounts.application.ports.in;

import com.sociame.app.core.usecases.accounts.domain.GetCurrentAccountCommand;
import com.sociame.app.core.usecases.accounts.domain.GetCurrentAccountResponse;

public interface GetCurrentAccountUseCase {

    GetCurrentAccountResponse handleCommand(GetCurrentAccountCommand command);

}
