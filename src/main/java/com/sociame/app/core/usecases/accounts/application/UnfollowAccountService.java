package com.sociame.app.core.usecases.accounts.application;

import com.sociame.app.core.usecases.accounts.application.ports.in.UnfollowAccountUseCase;
import com.sociame.app.core.usecases.accounts.application.ports.out.GetCurrentAccountPort;
import com.sociame.app.core.usecases.accounts.application.ports.out.UnfollowAccountServicePort;
import com.sociame.app.core.usecases.accounts.domain.Account;
import com.sociame.app.core.usecases.accounts.domain.UnfollowAccountCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UnfollowAccountService implements UnfollowAccountUseCase {

    private final GetCurrentAccountPort accountPort;

    private final UnfollowAccountServicePort unfollowPort;

    @Override
    public boolean handleCommand(UnfollowAccountCommand command) {
        Optional<Account> accountOptional = accountPort.getCurrentAccount(command.username());

        if (accountOptional.isEmpty()) return false;

        Account currentAccount = accountOptional.get();

        // @TODO: At this point, after implementing the follower/following list we will apply the proper business logic in order to allow the unfollowing.

        return unfollowPort.unfollow(currentAccount.getId().id(), command.accountId());
    }
}
