package com.sociame.app.core.usecases.accounts.application;

import com.sociame.app.core.usecases.accounts.application.ports.in.FollowAccountUseCase;
import com.sociame.app.core.usecases.accounts.application.ports.out.FollowAccountServicePort;
import com.sociame.app.core.usecases.accounts.application.ports.out.GetCurrentAccountPort;
import com.sociame.app.core.usecases.accounts.domain.Account;
import com.sociame.app.core.usecases.accounts.domain.FollowAccountCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowAccountService implements FollowAccountUseCase {

    private final GetCurrentAccountPort accountPort;

    private final FollowAccountServicePort followPort;

    @Override
    public boolean handleCommand(FollowAccountCommand command) {
        Optional<Account> accountOptional = accountPort.getCurrentAccount(command.username());

        if (accountOptional.isEmpty()) return false;

        Account currentAccount = accountOptional.get();

        // @TODO: At this point, after implementing the follower/following list we will apply the proper business logic in order to allow the following.

        return followPort.follow(currentAccount.getId().id(), command.accountId());
    }

}
