package com.sociame.app.core.usecases.posts.adapters.out;

import com.sociame.app.core.usecases.accounts.domain.Follower;
import com.sociame.app.core.usecases.posts.application.ports.out.GetFollowersAuthorsPort;
import com.sociame.app.core.usecases.posts.domain.Author;
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
public class GetFollowersAuthorsAdapter implements GetFollowersAuthorsPort {

    private final JdbcTemplate db;

    @Override
    public List<Author> getFollowers(long authorId) {
        try {
            
            return db.query(
                    """
                    SELECT
                        f.follower AS follower,
                        u.username AS username,
                        a.first_name AS first_name,
                        a.last_name AS last_name,
                        a.plan
                    FROM follow f
                    JOIN account a
                    ON (a.id = f.follower AND f.following = ?)
                    JOIN users u
                    ON (a.user_id = u.id)
                    """,
                    (result, rowNum) -> new Author(
                            result.getLong("follower"),
                            result.getString("username"),
                            result.getString("first_name"),
                            result.getString("last_name"),
                            result.getInt("plan")
                    ),
                    authorId
            );

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }
}
