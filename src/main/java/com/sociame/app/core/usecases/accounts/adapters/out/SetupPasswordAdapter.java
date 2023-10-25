package com.sociame.app.core.usecases.accounts.adapters.out;

import com.sociame.app.core.usecases.accounts.application.ports.out.SetupPasswordServicePort;
import com.sociame.app.core.usecases.accounts.domain.SetupPasswordCommand;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class SetupPasswordAdapter implements SetupPasswordServicePort {

    private final JdbcTemplate db;

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public String setupPassword(SetupPasswordCommand command) {

        try {
            FindAccountResult accountResult = db.queryForObject(
                    """
                    SELECT
                        id,
                        user_id
                    FROM account
                    WHERE verification_token = ? AND pin = ?
                    """,
                    (result, rowNumber) -> new FindAccountResult(
                            result.getLong("id"),
                            result.getLong("user_id")
                    ),
                    command.verificationToken(),
                    command.pin()
            );

            log.info("Account result: {}", accountResult);

            if (accountResult == null || accountResult.userId() == null || accountResult.accountId() == null) {
                return null;
            }

            String hashedPassword = passwordEncoder.encode(command.password());

            log.info("Hashed password: {}", hashedPassword);

            int affectedRows = db.update("UPDATE users SET password = ?, enabled = true WHERE id = ?", hashedPassword, accountResult.userId());

            if (affectedRows < 1) return null;

            affectedRows = db.update("UPDATE account SET verified = true WHERE id = ?", accountResult.accountId());

            if (affectedRows < 1) return null;

            return db.queryForObject(
                    """
                    SELECT
                        username
                    FROM users
                    WHERE id = ?
                    """,
                    (result, rowNumber) -> result.getString("username"),
                    accountResult.userId()
            );
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return null;

    }

    record FindAccountResult(
            Long accountId,
            Long userId
    ) {}

}
