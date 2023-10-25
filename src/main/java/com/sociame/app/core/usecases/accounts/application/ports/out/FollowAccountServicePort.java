package com.sociame.app.core.usecases.accounts.application.ports.out;

import com.sociame.app.core.usecases.accounts.domain.FollowAccountCommand;

public interface FollowAccountServicePort {

    boolean follow(long followerId, long followingId);

}
