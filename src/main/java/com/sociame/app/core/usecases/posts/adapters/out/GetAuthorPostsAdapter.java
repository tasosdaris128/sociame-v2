package com.sociame.app.core.usecases.posts.adapters.out;

import com.sociame.app.core.usecases.posts.application.ports.out.GetAuthorPostsPort;
import com.sociame.app.core.usecases.posts.domain.responses.AuthorResponse;
import com.sociame.app.core.usecases.posts.domain.responses.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetAuthorPostsAdapter implements GetAuthorPostsPort {

    private final JdbcTemplate db;

    @Override
    public List<PostResponse> getAuthorPosts(AuthorResponse author) {
        try {
            return db.query(
                    """
                    SELECT
                        id,
                        title,
                        body,
                        author_id
                    FROM post
                    WHERE author_id = ? LIMIT 100
                    """,
                    (result, rowNumber) -> new PostResponse(
                            result.getLong("id"),
                            result.getString("title"),
                            result.getString("body"),
                            author,
                            new ArrayList<>()
                    ),
                    author.id()
            );
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
