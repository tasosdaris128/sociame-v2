package com.sociame.app.core.usecases.accounts.adapters.out;

import com.sociame.app.core.usecases.accounts.application.ports.out.UnfollowAccountServicePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class UnfollowAccountAdapter implements UnfollowAccountServicePort {

    private final JdbcTemplate db;

    @Override
    public boolean unfollow(long followerId, long followingId) {
        return true;
    }
}
