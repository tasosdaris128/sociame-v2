package com.sociame.app.core.usecases.users.adapters.out;

import com.sociame.app.config.web.KnownRuntimeError;
import com.sociame.app.core.usecases.users.application.ports.out.UserDetailsServicePort;
import com.sociame.app.core.usecases.users.domain.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserAdapter implements UserDetailsServicePort {

    private final JdbcTemplate db;

    @Override
    public Optional<UserDetails> loadUserByUsername(String username) {
        if (username == null) {
            return Optional.empty();
        }

        try {
            UserDetails user = db.queryForObject(
                    """
                        SELECT
                            username,
                            password,
                            authorities,
                            enabled,
                            expired,
                            locked,
                            credentials_expired
                        FROM users
                        WHERE username=?
                    """,
                    (result, rowNumber) -> new UserDetailsImpl(
                            result.getString("username"),
                            result.getString("password"),
                            AuthorityUtils.createAuthorityList(),
                            result.getBoolean("enabled"),
                            result.getBoolean("expired"),
                            result.getBoolean("locked"),
                            result.getBoolean("credentials_expired")
                    ),
                    username
            );

            return Optional.ofNullable(user);
        } catch (IncorrectResultSetColumnCountException | EmptyResultDataAccessException e) {
            log.error(e.getMessage(), e);
            throw new KnownRuntimeError("User not found", e, KnownRuntimeError.ErrorType.NOT_FOUND);
        }
    }

}
