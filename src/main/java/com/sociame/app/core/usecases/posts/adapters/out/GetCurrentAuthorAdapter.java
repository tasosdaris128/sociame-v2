package com.sociame.app.core.usecases.posts.adapters.out;

import com.sociame.app.core.usecases.posts.application.ports.out.GetCurrentAuthorPort;
import com.sociame.app.core.usecases.posts.domain.Author;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class GetCurrentAuthorAdapter implements GetCurrentAuthorPort {

    private final JdbcTemplate db;

    @Override
    public Optional<Author> getCurrentAuthor(String username) {
        if (username == null) {
            return Optional.empty();
        }

        try {
            Author author = db.queryForObject(
                    """
                        SELECT
                            account_id,
                            username,
                            first_name,
                            last_name,
                            plan
                        FROM user_account(?)
                    """,
                    (result, rowNumber) -> new Author(
                            result.getLong("account_id"),
                            result.getString("username"),
                            result.getString("first_name"),
                            result.getString("last_name"),
                            result.getInt("plan")
                    ),
                    username
            );

            if (author == null) return Optional.empty();

            return Optional.of(author);
        } catch (IncorrectResultSetColumnCountException | EmptyResultDataAccessException e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

}
