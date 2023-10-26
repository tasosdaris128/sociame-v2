package com.sociame.app.core.usecases.posts.adapters.out;

import com.sociame.app.core.usecases.posts.application.ports.out.GetPostPort;
import com.sociame.app.core.usecases.posts.domain.Author;
import com.sociame.app.core.usecases.posts.domain.Post;
import com.sociame.app.core.usecases.posts.domain.PostId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class GetPostAdapter implements GetPostPort {

    private final JdbcTemplate db;

    @Override
    public Optional<Post> getPost(long postId) {
        try {
            Post post = db.queryForObject(
                    """
                    SELECT
                        id,
                        title,
                        body,
                        author_id
                    FROM account
                    WHERE id = ?
                    """,
                    (result, rowNumber) -> {
                        long authorId = result.getLong("author_id");

                        Author author = db.queryForObject(
                                """
                                    SELECT
                                        a.id AS id,
                                        u.username AS username,
                                        a.first_name AS first_name,
                                        a.last_name AS last_name,
                                        a.plan AS plan
                                    FROM account a
                                    JOIN users u
                                    ON (a.id = ? AND a.user_id = u.id)
                                """,
                                (r, rowNum) -> new Author(
                                        r.getLong("id"),
                                        r.getString("username"),
                                        r.getString("first_name"),
                                        r.getString("last_name"),
                                        r.getInt("plan")
                                ),
                                authorId
                        );

                        return new Post(
                                new PostId(result.getLong("id")),
                                result.getString("title"),
                                result.getString("body"),
                                author,
                                new ArrayList<>()
                        );
                    },
                    postId
            );

            return Optional.ofNullable(post);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

}
