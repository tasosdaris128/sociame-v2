package com.sociame.app.core.usecases.posts.adapters.out;

import com.sociame.app.core.usecases.posts.application.ports.out.CreatePostPort;
import com.sociame.app.core.usecases.posts.domain.Post;
import com.sociame.app.core.usecases.posts.domain.responses.CreatePostResponse;
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
public class CreatePostAdapter implements CreatePostPort {

    private final JdbcTemplate db;

    @Override
    public CreatePostResponse createPost(Post post) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();

            int affectedRows = db.update(connection -> {
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO post (title, body, author_id) VALUES (?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                );

                statement.setString(1, post.getTitle());
                statement.setString(2, post.getBody());
                statement.setLong(3, post.getAuthor().id());

                return statement;
            }, keyHolder);

            if (affectedRows < 1) throw new RuntimeException("Unable to insert the new post into the database.");

            Long postId = (Long) keyHolder.getKey();

            if (postId == null) throw new RuntimeException("Unable to retrieve the ID of the latest post.");

            return new CreatePostResponse(postId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

}
