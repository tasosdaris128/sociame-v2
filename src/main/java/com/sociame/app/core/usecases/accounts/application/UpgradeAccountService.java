package com.sociame.app.core.usecases.accounts.application;

import com.sociame.app.core.usecases.accounts.application.ports.in.UpgradeAccountUseCase;
import com.sociame.app.core.usecases.accounts.application.ports.out.UpgradeAccountServicePort;
import com.sociame.app.core.usecases.accounts.domain.Account;
import com.sociame.app.core.usecases.accounts.domain.UpgradeAccountCommand;
import com.sociame.app.core.usecases.accounts.domain.UpgradedAccountResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpgradeAccountService implements UpgradeAccountUseCase {

    private final UpgradeAccountServicePort port;

    @Override
    public Optional<UpgradedAccountResponse> handleCommand(UpgradeAccountCommand command) {
        Optional<Account> optionalCurrentAccount =  port.getCurrentAccount(command.username());

        log.info("Current account optional: {}", optionalCurrentAccount);

        if (optionalCurrentAccount.isEmpty()) return Optional.empty();

        Account currentAccount = optionalCurrentAccount.get();

        if (!currentAccount.isValidPlan(command.plan())) return Optional.empty();

        log.info("Plan is valid...");

        if (!currentAccount.canUpgrade()) return Optional.empty();

        log.info("Can downgrade...");

        Optional<Account> optionalUpgradedAccount = port.upgradeAccount(command);

        log.info("Upgraded account optional: {}", optionalUpgradedAccount);

        if (optionalUpgradedAccount.isEmpty()) return Optional.empty();

        Account upgradedAccount = optionalUpgradedAccount.get();

        return Optional.of(new UpgradedAccountResponse(
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
