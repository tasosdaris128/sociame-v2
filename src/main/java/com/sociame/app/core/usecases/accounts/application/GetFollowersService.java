package com.sociame.app.core.usecases.accounts.application;

import com.sociame.app.core.usecases.accounts.application.ports.in.GetFollowersUseCase;
import com.sociame.app.core.usecases.accounts.application.ports.out.GetCurrentAccountPort;
import com.sociame.app.core.usecases.accounts.application.ports.out.GetFollowersServicePorts;
import com.sociame.app.core.usecases.accounts.domain.Account;
import com.sociame.app.core.usecases.accounts.domain.GetFollowersCommand;
import com.sociame.app.core.usecases.accounts.domain.GetFollowersResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetFollowersService implements GetFollowersUseCase {

    private final GetCurrentAccountPort accountPort;

    private final GetFollowersServicePorts followersPort;

    @Override
    public GetFollowersResponse handleCommand(GetFollowersCommand command) {
        Optional<Account> accountOptional = accountPort.getCurrentAccount(command.username());

        if (accountOptional.isEmpty()) return new GetFollowersResponse(new ArrayList<>());

        Account currentAccount = accountOptional.get();

        return new GetFollowersResponse(followersPort.getFollowers(currentAccount.getId().id()));
    }

}
