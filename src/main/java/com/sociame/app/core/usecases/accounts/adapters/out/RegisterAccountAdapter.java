package com.sociame.app.core.usecases.accounts.adapters.out;

import com.sociame.app.core.usecases.accounts.application.ports.out.RegisterAccountServicePort;
import com.sociame.app.core.usecases.accounts.domain.RegisterAccountCommand;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class RegisterAccountAdapter implements RegisterAccountServicePort {

    private final JdbcTemplate db;

    @Override
    public boolean createUserAccount(
            RegisterAccountCommand command,
            String verificationToken,
            int pin
    ) {
        if (command.username() == null) return false;

        try {
            int userInsertAffectedRows = db.update(
                    "INSERT INTO users (username) VALUES (?)",
                    command.username()
            );

            log.info("User insert affected rows: {}", userInsertAffectedRows);

            if (userInsertAffectedRows < 1) return false;

            Integer userId = db.queryForObject("SELECT currval('users_id_seq')", Integer.class);

            if (userId == null) return false;

            log.info("User id: {}", userId);

            int accountInsertAffectedRows = db.update(
                    """
                    INSERT INTO account (first_name, last_name, gender, plan, pin, verification_token, user_id)
                    VALUES (?, ?, ?, ?, ?, ?, ?)
                    """,
                    command.firstName(),
                    command.lastName(),
                    command.gender(),
                    command.plan(),
                    pin,
                    verificationToken,
                    userId
            );

            log.info("Account insert affected rows: {}", accountInsertAffectedRows);

            if (accountInsertAffectedRows < 1) return false;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }

        return true;
    }
}
