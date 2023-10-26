package com.sociame.app.core.usecases.accounts.application;

import com.sociame.app.core.usecases.accounts.application.ports.in.SearchAccountUseCase;
import com.sociame.app.core.usecases.accounts.application.ports.out.SearchAccountPort;
import com.sociame.app.core.usecases.accounts.domain.SearchAccountCommand;
import com.sociame.app.core.usecases.accounts.domain.SearchAccountResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchAccountService implements SearchAccountUseCase {

    private final SearchAccountPort port;

    @Override
    public SearchAccountResponse handleCommand(SearchAccountCommand command) {
        return new SearchAccountResponse(port.searchAccount(command.term()));
    }
}
