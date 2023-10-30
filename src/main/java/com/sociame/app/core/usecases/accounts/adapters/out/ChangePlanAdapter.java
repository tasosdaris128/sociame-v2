package com.sociame.app.core.usecases.accounts.adapters.out;

import com.sociame.app.core.usecases.accounts.application.ports.out.ChangePlanServicePort;
import com.sociame.app.core.usecases.accounts.domain.ChangePlanCommand;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class ChangePlanAdapter implements ChangePlanServicePort {

    private final JdbcTemplate db;

    @Override
    public boolean changePlan(ChangePlanCommand command, Long accountId) {
        log.info("Upgrading: {}", command);

        int affectedRows = db.update(
                "UPDATE account SET plan = ? WHERE id = ?",
                command.plan(),
                accountId
        );

        return  (affectedRows > 0);
    }

}
