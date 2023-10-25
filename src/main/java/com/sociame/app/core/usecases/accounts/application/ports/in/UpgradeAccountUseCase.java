package com.sociame.app.core.usecases.accounts.application.ports.in;

import com.sociame.app.core.usecases.accounts.domain.UpgradeAccountCommand;
import com.sociame.app.core.usecases.accounts.domain.UpgradedAccountResponse;

import java.util.Optional;

public interface UpgradeAccountUseCase {

    Optional<UpgradedAccountResponse> handleCommand(UpgradeAccountCommand command);

}
