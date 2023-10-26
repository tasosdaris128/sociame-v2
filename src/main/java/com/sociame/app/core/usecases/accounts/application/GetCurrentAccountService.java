package com.sociame.app.core.usecases.accounts.application;

import com.sociame.app.core.usecases.accounts.application.ports.in.GetCurrentAccountUseCase;
import com.sociame.app.core.usecases.accounts.application.ports.out.GetCurrentAccountPort;
import com.sociame.app.core.usecases.accounts.domain.Account;
import com.sociame.app.core.usecases.accounts.domain.GetCurrentAccountCommand;
import com.sociame.app.core.usecases.accounts.domain.GetCurrentAccountResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetCurrentAccountService implements GetCurrentAccountUseCase {

    private final GetCurrentAccountPort port;

    @Override
    public GetCurrentAccountResponse handleCommand(GetCurrentAccountCommand command) {
        Optional<Account> optinal = port.getCurrentAccount(command.username());

        if (optinal.isEmpty()) return null;

        Account account = optinal.get();

        return new GetCurrentAccountResponse(
                account.getId().id(),
                account.getUserId().id(),
                account.getUsername(),
                account.getFirstName(),
                account.getLastName(),
                account.getGender(),
                account.getPlan(),
                account.getFollowers(),
                account.getFollowings()
        );
    }
}
