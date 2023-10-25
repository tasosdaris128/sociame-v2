package com.sociame.app.core.usecases.accounts.application;

import com.sociame.app.core.usecases.accounts.application.ports.in.SetupPasswordUseCase;
import com.sociame.app.core.usecases.accounts.application.ports.out.SetupPasswordServicePort;
import com.sociame.app.core.usecases.accounts.domain.SetupPasswordCommand;
import com.sociame.app.utils.Mail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SetupPasswordService implements SetupPasswordUseCase {

    private final Mail mail;

    private final SetupPasswordServicePort port;

    @Override
    public boolean handleCommand(SetupPasswordCommand command) {
        String email = port.setupPassword(command);

        log.info("Email: {}", email);

        if (email == null) return false;

        new Thread(() -> mail.send(
                email,
                "Finished registration",
                "Welcome to sociame!"
        )).start();

        return true;
    }

}
