package com.sociame.app.core.usecases.accounts.adapters.out;

import com.sociame.app.core.usecases.accounts.application.ports.out.SetupPasswordServicePort;
import com.sociame.app.core.usecases.accounts.domain.Account;
import com.sociame.app.core.usecases.accounts.domain.SetupPasswordCommand;
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
public class SetupPasswordAdapter implements SetupPasswordServicePort {

    private final JdbcTemplate db;

    @Override
    public Optional<Account> setupPassword(SetupPasswordCommand command) {
        return Optional.empty();
    }

}
