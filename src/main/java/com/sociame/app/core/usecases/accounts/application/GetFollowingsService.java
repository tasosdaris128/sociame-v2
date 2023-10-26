package com.sociame.app.core.usecases.accounts.application;

import com.sociame.app.core.usecases.accounts.application.ports.in.GetFollowingsUseCase;
import com.sociame.app.core.usecases.accounts.application.ports.out.GetCurrentAccountPort;
import com.sociame.app.core.usecases.accounts.application.ports.out.GetFollowingsServicePort;
import com.sociame.app.core.usecases.accounts.domain.Account;
import com.sociame.app.core.usecases.accounts.domain.GetFollowingsCommand;
import com.sociame.app.core.usecases.accounts.domain.GetFollowingsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetFollowingsService implements GetFollowingsUseCase {

    private final GetCurrentAccountPort accountPort;

    private final GetFollowingsServicePort followingsPort;

    @Override
    public GetFollowingsResponse handleCommand(GetFollowingsCommand command) {
        Optional<Account> accountOptional = accountPort.getCurrentAccount(command.username());

        if (accountOptional.isEmpty()) return new GetFollowingsResponse(new ArrayList<>());

        Account currentAccount = accountOptional.get();

        return new GetFollowingsResponse(followingsPort.getFollowings(currentAccount.getId().id()));
    }

}
