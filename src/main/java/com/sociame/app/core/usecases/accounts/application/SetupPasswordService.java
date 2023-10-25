package com.sociame.app.core.usecases.accounts.application;

import com.sociame.app.core.usecases.accounts.application.ports.in.SetupPasswordUseCase;
import com.sociame.app.core.usecases.accounts.application.ports.out.SetupPasswordServicePort;
import com.sociame.app.core.usecases.accounts.domain.Account;
import com.sociame.app.core.usecases.accounts.domain.SetupPasswordCommand;
import com.sociame.app.utils.Mail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SetupPasswordService implements SetupPasswordUseCase {

    private final Mail mail;

    private final SetupPasswordServicePort port;

    @Override
    public boolean handleCommand(SetupPasswordCommand command) {
        Optional<Account> account = port.setupPassword(command);

        return true;
    }

}
