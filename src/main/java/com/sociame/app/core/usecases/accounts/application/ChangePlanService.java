package com.sociame.app.core.usecases.accounts.application;

import com.sociame.app.core.usecases.accounts.application.ports.in.ChangePlanUseCase;
import com.sociame.app.core.usecases.accounts.application.ports.out.ChangePlanServicePort;
import com.sociame.app.core.usecases.accounts.application.ports.out.GetCurrentAccountPort;
import com.sociame.app.core.usecases.accounts.domain.Account;
import com.sociame.app.core.usecases.accounts.domain.ChangePlanCommand;
import com.sociame.app.core.usecases.accounts.domain.ChangePlanResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChangePlanService implements ChangePlanUseCase {

    private final ChangePlanServicePort servicePort;

    private final GetCurrentAccountPort accountPort;

    @Override
    public Optional<ChangePlanResponse> handleCommand(ChangePlanCommand command) {
        Optional<Account> optionalCurrentAccount = accountPort.getCurrentAccount(command.username());

        log.info("Current account optional: {}", optionalCurrentAccount);

        if (optionalCurrentAccount.isEmpty()) return Optional.empty();

        Account currentAccount = optionalCurrentAccount.get();

        if (currentAccount.cannotChangePlan(command.plan())) return Optional.empty();

        log.info("Can change plan...");

        Optional<Account> optionalUpgradedAccount = servicePort.changePlan(command);

        log.info("Changed account optional: {}", optionalUpgradedAccount);

        if (optionalUpgradedAccount.isEmpty()) return Optional.empty();

        Account upgradedAccount = optionalUpgradedAccount.get();

        return Optional.of(new ChangePlanResponse(
                upgradedAccount.getId().id(),
                upgradedAccount.getUserId().id(),
                upgradedAccount.getUsername(),
                upgradedAccount.getFirstName(),
                upgradedAccount.getLastName(),
                upgradedAccount.getGender(),
                upgradedAccount.getPlan()
        ));
    }

}
