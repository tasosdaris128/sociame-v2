package com.sociame.app.core.usecases.accounts.adapters.out;

import com.sociame.app.core.usecases.accounts.application.ports.out.UpgradeAccountServicePort;
import com.sociame.app.core.usecases.accounts.domain.Account;
import com.sociame.app.core.usecases.accounts.domain.AccountId;
import com.sociame.app.core.usecases.accounts.domain.UpgradeAccountCommand;
import com.sociame.app.core.usecases.accounts.domain.UserId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class UpgradeAccountAdapter implements UpgradeAccountServicePort {

    private final JdbcTemplate db;

    @Override
    public Optional<Account> getCurrentAccount(String username) {
        if (username == null) {
            return Optional.empty();
        }

        try {
            Account account = db.queryForObject(
                    """
                        SELECT
                            account_id,
                            user_id,
                            username,
                            first_name,
                            last_name,
                            gender,
                            dob,
                            plan,
                            verified
                        FROM user_account(?)
                    """,
                    (result, rowNumber) -> new Account(
                            new AccountId(result.getLong("account_id")),
                            new UserId(result.getLong("user_id")),
                            result.getString("username"),
                            result.getString("first_name"),
                            result.getString("last_name"),
                            result.getString("gender"),
                            result.getInt("plan"),
                            null,
                            0,
                            true
                    ),
                    username
            );

            return Optional.ofNullable(account);
        } catch (IncorrectResultSetColumnCountException | EmptyResultDataAccessException e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Account> upgradeAccount(UpgradeAccountCommand command) {
        log.info("Upgrading: {}", command);

        Optional<Account> optional = getCurrentAccount(command.username());

        if (optional.isEmpty()) return Optional.empty();

        Account currentAccount = optional.get();

        log.info("Current account before update: {}", currentAccount);

        int affectedRows = db.update(
                "UPDATE account SET plan = ? WHERE id = ?",
                command.plan(),
                currentAccount.getId().id()
        );

        if (affectedRows < 1) return Optional.empty();

        return getCurrentAccount(command.username());
    }

}
