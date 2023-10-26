package com.sociame.app.core.usecases.accounts.application.ports.in;

import com.sociame.app.core.usecases.accounts.domain.GetFollowersCommand;
import com.sociame.app.core.usecases.accounts.domain.GetFollowersResponse;

public interface GetFollowersUseCase {

    GetFollowersResponse handleCommand(GetFollowersCommand command);

}
