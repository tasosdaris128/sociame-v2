package com.sociame.app.core.usecases.accounts.application.ports.out;

public interface UnfollowAccountServicePort {

    boolean unfollow(long followerId, long followingId);

}
