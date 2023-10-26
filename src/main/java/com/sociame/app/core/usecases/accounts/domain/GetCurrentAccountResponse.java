package com.sociame.app.core.usecases.accounts.domain;

import java.util.List;

public record GetCurrentAccountResponse(
        Long id,

        Long userId,

        String username,

        String firstName,

        String lastName,

        String gender,

        int plan,

        List<Follower> followers,

        List<Following> followings
) {}
