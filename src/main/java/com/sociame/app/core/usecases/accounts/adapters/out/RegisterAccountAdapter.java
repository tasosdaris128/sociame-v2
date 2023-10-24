package com.sociame.app.core.usecases.accounts.adapters.out;

import com.sociame.app.core.usecases.accounts.application.ports.out.RegisterAccountServicePort;
import com.sociame.app.core.usecases.accounts.domain.RegisterAccountCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegisterAccountAdapter implements RegisterAccountServicePort {
    @Override
    public boolean createUserAccount(RegisterAccountCommand command, String verificationToken, int pin) {
        return true;
    }
}
