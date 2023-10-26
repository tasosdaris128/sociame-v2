package com.sociame.app.core.usecases.users.adapters.out;

import com.sociame.app.core.usecases.users.application.ports.out.UserAccountServicePort;
import com.sociame.app.core.usecases.users.domain.AccountId;
import com.sociame.app.core.usecases.users.domain.User;
import com.sociame.app.core.usecases.users.domain.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Deprecated
@Slf4j
@Component
@RequiredArgsConstructor
public class UserAccountAdapter implements UserAccountServicePort {

    private final JdbcTemplate db;

    @Override
    public Optional<User> getUserAccountByUsername(String username) {
        if (username == null) {
            return Optional.empty();
        }

        try {
            User user = db.queryForObject(
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
                    (result, rowNumber) -> new User(
                            new UserId(result.getLong("user_id")),
                            new AccountId(result.getLong("account_id")),
                            null,
                            result.getString("username"),
                            result.getString("first_name"),
                            result.getString("last_name"),
                            result.getString("gender"),
                            result.getInt("plan"),
                            null,
                            null,
                            true,
                            false,
                            false,
                            false,
                            result.getBoolean("verified")
                    ),
                    username
            );

            return Optional.ofNullable(user);
        } catch (IncorrectResultSetColumnCountException | EmptyResultDataAccessException e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
    }
}
