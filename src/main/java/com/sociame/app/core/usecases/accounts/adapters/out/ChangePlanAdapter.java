package com.sociame.app.core.usecases.accounts.adapters.out;

import com.sociame.app.core.usecases.accounts.application.ports.out.ChangePlanServicePort;
import com.sociame.app.core.usecases.accounts.application.ports.out.GetCurrentAccountPort;
import com.sociame.app.core.usecases.accounts.domain.Account;
import com.sociame.app.core.usecases.accounts.domain.ChangePlanCommand;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class ChangePlanAdapter implements ChangePlanServicePort {

    private final JdbcTemplate db;

    private final GetCurrentAccountPort accountPort;

    @Override
    public Optional<Account> changePlan(ChangePlanCommand command) {
        log.info("Upgrading: {}", command);

        Optional<Account> optional = accountPort.getCurrentAccount(command.username());

        if (optional.isEmpty()) return Optional.empty();

        Account currentAccount = optional.get();

        log.info("Current account before update: {}", currentAccount);

        int affectedRows = db.update(
                "UPDATE account SET plan = ? WHERE id = ?",
                command.plan(),
                currentAccount.getId().id()
        );

        if (affectedRows < 1) return Optional.empty();

        return accountPort.getCurrentAccount(command.username());
    }

}
