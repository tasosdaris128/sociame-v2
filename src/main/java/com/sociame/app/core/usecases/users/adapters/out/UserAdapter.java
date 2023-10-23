package com.sociame.app.core.usecases.users.adapters.out;

import com.sociame.app.config.web.security.JWTSecurityProperties;
import com.sociame.app.core.usecases.users.application.ports.out.UserDetailsServicePort;
import com.sociame.app.core.usecases.users.domain.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserAdapter implements UserDetailsServicePort {

    private final JdbcTemplate db;

    private final JWTSecurityProperties properties;

    @Override
    public Optional<UserDetails> loadUserByUsernameAndPassword(String username, String password) {
        if (username == null || password == null) {
            return Optional.empty();
        }

        try {
            UserDetails user = db.queryForObject(
                    """
                        SELECT
                            username,
                            password
                        FROM users
                        WHERE username=? AND password = crypt(?, ?)
                    """,
                    (result, rowNumber) -> new UserDetailsImpl(
                            result.getString("username"),
                            result.getString("password")
                    ),
                    username,
                    password,
                    properties.getSalt()
            );

            return Optional.ofNullable(user);
        } catch (IncorrectResultSetColumnCountException | EmptyResultDataAccessException e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

}
