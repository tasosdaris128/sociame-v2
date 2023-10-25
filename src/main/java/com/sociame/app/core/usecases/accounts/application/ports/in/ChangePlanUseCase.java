package com.sociame.app.core.usecases.accounts.application.ports.in;

import com.sociame.app.core.usecases.accounts.domain.ChangePlanCommand;
import com.sociame.app.core.usecases.accounts.domain.ChangePlanResponse;

import java.util.Optional;

public interface ChangePlanUseCase {

    Optional<ChangePlanResponse> handleCommand(ChangePlanCommand command);

}
