package com.sociame.app.core.usecases.posts.adapters.out;

import com.sociame.app.core.usecases.posts.application.ports.out.CreateCommentPort;
import com.sociame.app.core.usecases.posts.domain.Author;
import com.sociame.app.core.usecases.posts.domain.Comment;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class CreateCommentAdapter implements CreateCommentPort {

    private final JdbcTemplate db;

    @Override
    public Optional<Comment> createComment(long postId, String body, Author author) {
        try {
            int affectedRows = db.update(
                    """
                    INSERT INTO comment (body, post_id, author_id) VALUES (?, ?, ?)
                    """,
                    body,
                    postId,
                    author.id()
            );

            if (affectedRows < 1) return Optional.empty();

            Integer commentId = db.queryForObject("SELECT currval('comment_id_seq')", Integer.class);

            if (commentId == null) return Optional.empty();

            Comment comment = db.queryForObject(
                    """
                    SELECT
                        id,
                        body,
                        post_id
                    FROM comment
                    WHERE id =?
                    """,
                    (result, rowNumber) -> new Comment(
                            result.getLong("id"),
                            result.getString("body"),
                            result.getLong("post_id"),
                            author
                    ),
                    commentId
            );

            return Optional.ofNullable(comment);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

}
