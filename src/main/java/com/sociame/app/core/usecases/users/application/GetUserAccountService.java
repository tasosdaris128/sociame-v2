package com.sociame.app.core.usecases.users.application;

import com.sociame.app.core.usecases.users.application.ports.in.GetUserAccountUseCase;
import com.sociame.app.core.usecases.users.application.ports.out.UserAccountServicePort;
import com.sociame.app.core.usecases.users.domain.GetUserAccountCommand;
import com.sociame.app.core.usecases.users.domain.GetUserAccountResponse;
import com.sociame.app.core.usecases.users.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetUserAccountService implements GetUserAccountUseCase {

    private final UserAccountServicePort port;

    @Override
    public GetUserAccountResponse handleCommand(GetUserAccountCommand command) {
        Optional<User> optional = port.getUserAccountByUsername(command.username());

        if (optional.isEmpty()) return null;

        User user = optional.get();

        return new GetUserAccountResponse(
                user.getAccountId().accountId(),
                user.getId().id(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getGender(),
                user.getPlan(),
                user.isVerified()
        );
    }
}
