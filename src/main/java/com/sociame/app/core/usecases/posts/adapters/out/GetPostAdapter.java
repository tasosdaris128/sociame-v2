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
            Post incompletePost = db.queryForObject(
                    """
                    SELECT
                        id,
                        title,
                        body,
                        author_id
                    FROM account
                    WHERE id = ?
                    """,
                    (result, rowNumber) -> new Post(
                            new PostId(result.getLong("id")),
                            result.getString("title"),
                            result.getString("body"),
                            new Author(
                                    result.getLong("author_id"),
                                    "",
                                    "",
                                    "",
                                    0
                            ),
                            new ArrayList<>()
                    ),
                    postId
            );

            if (incompletePost == null) return Optional.empty();

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
                    (result, rowNumber) -> new Author(
                            result.getLong("id"),
                            result.getString("username"),
                            result.getString("first_name"),
                            result.getString("last_name"),
                            result.getInt("plan")
                    ),
                    incompletePost.getAuthor().id()
            );

            if (author == null) return Optional.empty();

            return Optional.of(new Post(
                    incompletePost.getPostId(),
                    incompletePost.getTitle(),
                    incompletePost.getBody(),
                    author,
                    incompletePost.getComments()
            ));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

}
