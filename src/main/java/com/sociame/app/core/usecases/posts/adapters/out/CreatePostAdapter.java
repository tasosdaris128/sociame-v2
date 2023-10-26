package com.sociame.app.core.usecases.posts.adapters.out;

import com.sociame.app.core.usecases.posts.application.ports.out.CreatePostPort;
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
public class CreatePostAdapter implements CreatePostPort {

    private final JdbcTemplate db;

    @Override
    public Optional<Post> createPost(Post post) {
        try {
            int affectedRows = db.update(
                    """
                    INSERT INTO post (title, body, author_id) VALUES (?, ?, ?)
                    """,
                    post.getTitle(),
                    post.getBody(),
                    post.getAuthor().id()
            );

            if (affectedRows < 1) return Optional.empty();

            Integer postId = db.queryForObject("SELECT currval('post_id_seq')", Integer.class);

            if (postId == null) return Optional.empty();

            Post createdPost = db.queryForObject(
                    """
                    SELECT
                        id,
                        title,
                        body,
                        author_id
                    FROM post
                    WHERE id = ?
                    """,
                    (result, rowNumber) -> new Post(
                            new PostId(result.getLong("id")),
                            result.getString("title"),
                            result.getString("body"),
                            post.getAuthor(),
                            new ArrayList<>()
                    ),
                    postId
            );

            return Optional.ofNullable(createdPost);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

}
