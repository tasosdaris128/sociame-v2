package com.sociame.app.core.usecases.posts.adapters.out;

import com.sociame.app.config.web.KnownRuntimeError;
import com.sociame.app.core.usecases.posts.application.ports.out.GetCurrentAuthorPort;
import com.sociame.app.core.usecases.posts.domain.responses.AuthorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetCurrentAuthorAdapter implements GetCurrentAuthorPort {

    private final JdbcTemplate db;

    @Override
    public AuthorResponse getCurrentAuthor(String username) {
        if (username == null) {
            throw new KnownRuntimeError("Unable to retrieve current author's username.");
        }

        try {
            return db.queryForObject(
                    """
                        SELECT
                            account_id,
                            username,
                            first_name,
                            last_name,
                            plan
                        FROM user_account(?)
                    """,
                    (result, rowNumber) -> new AuthorResponse(
                            result.getLong("account_id"),
                            result.getString("username"),
                            result.getString("first_name"),
                            result.getString("last_name"),
                            result.getInt("plan")
                    ),
                    username
            );
        } catch (IncorrectResultSetColumnCountException | EmptyResultDataAccessException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
