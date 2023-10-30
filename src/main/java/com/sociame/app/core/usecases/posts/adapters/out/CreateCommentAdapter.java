package com.sociame.app.core.usecases.posts.adapters.out;

import com.sociame.app.core.usecases.posts.application.ports.out.CreateCommentPort;
import com.sociame.app.core.usecases.posts.domain.Author;
import com.sociame.app.core.usecases.posts.domain.Comment;
import com.sociame.app.core.usecases.posts.domain.responses.CreateCommentResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class CreateCommentAdapter implements CreateCommentPort {

    private final JdbcTemplate db;

    @Override
    public CreateCommentResponse createComment(long postId, String body, Author author) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();

            int affectedRows = db.update(connection -> {
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO comment (body, post_id, author_id) VALUES (?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                );

                statement.setString(1, body);
                statement.setLong(2, postId);
                statement.setLong(3, author.id());

                return statement;
            }, keyHolder);

            if (affectedRows < 1) throw new RuntimeException("Unable to insert comment into DB.");

            Long commentId = (Long) keyHolder.getKey();

            if (commentId == null) throw new RuntimeException("Unable to retrieve the ID of the comment.");

            return new CreateCommentResponse(commentId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw  new RuntimeException(e.getMessage());
        }
    }

    @Override
    public CreateCommentResponse createComment(Comment comment) {
        return createComment(comment.postId(), comment.body(), comment.author());
    }

}
