package com.sociame.app.core.usecases.accounts.domain;

import java.util.List;

public record GetFollowersResponse(List<Follower> followers) {}
