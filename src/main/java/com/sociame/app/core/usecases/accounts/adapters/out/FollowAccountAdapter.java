package com.sociame.app.core.usecases.accounts.adapters.out;

import com.sociame.app.core.usecases.accounts.application.ports.out.FollowAccountServicePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class FollowAccountAdapter implements FollowAccountServicePort {

    private final JdbcTemplate db;

    @Override
    public boolean follow(long followerId, long followingId) {

        int affectedRows = db.update(
                """
                INSERT INTO follow (follower, following) VALUES (?, ?)
                """,
                followerId,
                followingId
        );

        return (affectedRows > 0);
    }
}
