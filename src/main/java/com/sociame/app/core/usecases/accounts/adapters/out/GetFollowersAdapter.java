package com.sociame.app.core.usecases.accounts.adapters.out;

import com.sociame.app.core.usecases.accounts.application.ports.out.GetFollowersServicePorts;
import com.sociame.app.core.usecases.accounts.domain.Follower;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class GetFollowersAdapter implements GetFollowersServicePorts {

    private final JdbcTemplate db;

    @Override
    public List<Follower> getFollowers(long accountId) {
        try {

            return db.query(
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
                    accountId
            );

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

}
