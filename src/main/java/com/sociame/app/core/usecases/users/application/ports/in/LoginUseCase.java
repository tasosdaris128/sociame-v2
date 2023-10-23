package com.sociame.app.core.usecases.users.application.ports.in;

import com.sociame.app.core.usecases.users.domain.LoginCommand;
import com.sociame.app.core.usecases.users.domain.LoginResponse;

public interface LoginUseCase {
    LoginResponse handleCommand(LoginCommand command);
}
