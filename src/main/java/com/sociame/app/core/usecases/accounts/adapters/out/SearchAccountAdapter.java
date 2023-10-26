package com.sociame.app.core.usecases.accounts.adapters.out;

import com.sociame.app.core.usecases.accounts.application.ports.out.SearchAccountPort;
import com.sociame.app.core.usecases.accounts.domain.SearchedAccount;
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
public class SearchAccountAdapter implements SearchAccountPort {

    private final JdbcTemplate db;

    @Override
    public List<SearchedAccount> searchAccount(String term) {
        try {
            return db.query(
                    """
                    SELECT
                        id,
                        user_id,
                        username,
                        first_name,
                        last_name,
                        gender,
                        plan,
                        verified
                    FROM search_account(?)
                    """,
                    (result, rowNumber) -> new SearchedAccount(
                            result.getLong("id"),
                            result.getLong("user_id"),
                            result.getString("username"),
                            result.getString("first_name"),
                            result.getString("last_name"),
                            result.getString("gender")
                    ),
                    term
            );
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }
}
