package com.sociame.app.core.usecases.users.application.ports.in;

import com.sociame.app.core.usecases.users.domain.GetUserAccountCommand;
import com.sociame.app.core.usecases.users.domain.GetUserAccountResponse;

@Deprecated
public interface GetUserAccountUseCase {

    GetUserAccountResponse handleCommand(GetUserAccountCommand command);

}
