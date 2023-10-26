package com.sociame.app.core.usecases.accounts.adapters.out;

import com.sociame.app.core.usecases.accounts.application.ports.out.GetCurrentAccountPort;
import com.sociame.app.core.usecases.accounts.domain.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class GetCurrentAccountAdapter implements GetCurrentAccountPort {

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
                            true,
                            new ArrayList<>(),
                            new ArrayList<>()
                    ),
                    username
            );

            if (account == null) return Optional.empty();

            List<Follower> followers = db.query(
                    """
                    SELECT
                        f.follower AS follower,
                        a.first_name AS first_name,
                        a.last_name AS last_name
                    FROM follow f
                    JOIN account a
                    ON (a.id = f.follower AND f.following = ?)
                    """,
                    (result, rowNum) -> new Follower(
                            result.getLong("follower"),
                            result.getString("first_name"),
                            result.getString("last_name")
                    ),
                    account.getId().id()
            );

            List<Following> followings = db.query(
                    """
                    SELECT
                        f.following AS following,
                        a.first_name AS first_name,
                        a.last_name AS last_name
                    FROM follow f
                    JOIN account a
                    ON (a.id = f.following AND f.follower = ?)
                    """,
                    (result, rowNum) -> new Following(
                            result.getLong("following"),
                            result.getString("first_name"),
                            result.getString("last_name")
                    ),
                    account.getId().id()
            );

            return Optional.of(new Account(
                    account.getId(),
                    account.getUserId(),
                    account.getUsername(),
                    account.getFirstName(),
                    account.getLastName(),
                    account.getGender(),
                    account.getPlan(),
                    null,
                    0,
                    true,
                    followers,
                    followings
            ));
        } catch (IncorrectResultSetColumnCountException | EmptyResultDataAccessException e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
    }
}
