package com.sociame.app.core.usecases.posts.adapters.out;

import com.sociame.app.core.usecases.posts.application.ports.out.GetFollowingsAuthorsPort;
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
public class GetFollowingsAuthorsAdapter implements GetFollowingsAuthorsPort {

    private final JdbcTemplate db;

    @Override
    public List<Author> getFollowings(long authorId) {
        try {

            return db.query(
                    """
                    SELECT
                        f.following AS following,
                        u.username AS username,
                        a.first_name AS first_name,
                        a.last_name AS last_name,
                        a.plan
                    FROM follow f
                    JOIN account a
                    ON (a.id = f.following AND f.follower = ?)
                    JOIN users u
                    ON (a.user_id = u.id)
                    """,
                    (result, rowNum) -> new Author(
                            result.getLong("following"),
                            result.getString("username"),
                            result.getString("first_name"),
                            result.getString("last_name"),
                            result.getInt("plan")
                    ),
                    authorId
            );

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
