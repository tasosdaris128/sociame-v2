package com.sociame.app.core.usecases.posts.adapters.out;

import com.sociame.app.core.usecases.posts.application.ports.out.GetCommentsPort;
import com.sociame.app.core.usecases.posts.domain.Author;
import com.sociame.app.core.usecases.posts.domain.Comment;
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
public class GetCommentsAdapter implements GetCommentsPort {

    private final JdbcTemplate db;

    @Override
    public List<Comment> getComments(long postId) {
        try {

            return db.query(
                    """
                    SELECT
                        id,
                        body,
                        post_id,
                        author_id
                    FROM comment
                    WHERE post_id = ?
                    """,
                    (result,  rowNumber) -> {
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

                        return new Comment(
                                result.getLong("id"),
                                result.getString("body"),
                                result.getLong("post_id"),
                                author
                        );
                    },
                    postId
            );
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
