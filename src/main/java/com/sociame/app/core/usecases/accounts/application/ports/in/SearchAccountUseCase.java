package com.sociame.app.core.usecases.accounts.application.ports.in;

import com.sociame.app.core.usecases.accounts.domain.SearchAccountCommand;
import com.sociame.app.core.usecases.accounts.domain.SearchAccountResponse;

public interface SearchAccountUseCase {

    SearchAccountResponse handleCommand(SearchAccountCommand command);

}