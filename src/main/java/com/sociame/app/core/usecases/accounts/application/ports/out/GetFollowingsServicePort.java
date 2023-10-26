package com.sociame.app.core.usecases.accounts.application.ports.out;

import com.sociame.app.core.usecases.accounts.domain.Following;

import java.util.List;

public interface GetFollowingsServicePort {

    List<Following> getFollowings(long accountId);

}
