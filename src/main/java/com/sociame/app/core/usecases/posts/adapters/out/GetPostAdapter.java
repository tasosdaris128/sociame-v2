package com.sociame.app.core.usecases.posts.adapters.out;

import com.sociame.app.core.usecases.posts.application.ports.out.GetPostPort;
import com.sociame.app.core.usecases.posts.domain.Author;
import com.sociame.app.core.usecases.posts.domain.Comment;
import com.sociame.app.core.usecases.posts.domain.Post;
import com.sociame.app.core.usecases.posts.domain.PostId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
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
                    FROM post
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

                        List<Comment> comments = db.query(
                                """
                                SELECT
                                    id,
                                    body,
                                    post_id,
                                    author_id
                                FROM comment
                                WHERE post_id = ?
                                """,
                                (r,  rowNum) -> {
                                    long commentAuthorId = r.getLong("author_id");

                                    Author commentAuthor = db.queryForObject(
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
                                            (rsl, rNum) -> new Author(
                                                    rsl.getLong("id"),
                                                    rsl.getString("username"),
                                                    rsl.getString("first_name"),
                                                    rsl.getString("last_name"),
                                                    rsl.getInt("plan")
                                            ),
                                            commentAuthorId
                                    );

                                    return new Comment(
                                            r.getLong("id"),
                                            r.getString("body"),
                                            r.getLong("post_id"),
                                            commentAuthor
                                    );
                                },
                                postId
                        );

                        return new Post(
                                new PostId(result.getLong("id")),
                                result.getString("title"),
                                result.getString("body"),
                                author,
                                comments
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
