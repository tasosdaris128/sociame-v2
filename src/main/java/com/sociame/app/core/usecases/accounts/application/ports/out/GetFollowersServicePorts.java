package com.sociame.app.core.usecases.accounts.application.ports.out;

import com.sociame.app.core.usecases.accounts.domain.Follower;

import java.util.List;

public interface GetFollowersServicePorts {

    List<Follower> getFollowers(long accountId);

}
