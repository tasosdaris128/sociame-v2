package com.sociame.app.core.usecases.accounts.application.ports.in;

import com.sociame.app.core.usecases.accounts.domain.GetFollowingsCommand;
import com.sociame.app.core.usecases.accounts.domain.GetFollowingsResponse;

public interface GetFollowingsUseCase {

    GetFollowingsResponse handleCommand(GetFollowingsCommand command);

}
