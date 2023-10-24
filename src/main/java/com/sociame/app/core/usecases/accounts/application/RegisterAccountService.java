package com.sociame.app.core.usecases.accounts.application;

import com.sociame.app.core.usecases.accounts.application.ports.in.RegisterAccountUseCase;
import com.sociame.app.core.usecases.accounts.application.ports.out.RegisterAccountServicePort;
import com.sociame.app.core.usecases.accounts.domain.RegisterAccountCommand;
import com.sociame.app.utils.Mail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterAccountService implements RegisterAccountUseCase {

    private final Mail mail;

    private final RegisterAccountServicePort port;

    @Override
    public boolean handleCommand(RegisterAccountCommand command) {
        String verificationToken = UUID.randomUUID().toString();

        Random random = new Random();

        int pin = random.nextInt(10000);

        boolean result = port.createUserAccount(command, verificationToken, pin);

        if (result && (command.username() != null)) {
            new Thread(() -> mail.send(
                    command.username(),
                    "Finish registration",
                    RegistrationMailComposer.compose(pin, verificationToken)
            )).start();
        }

        return result;
    }

}
