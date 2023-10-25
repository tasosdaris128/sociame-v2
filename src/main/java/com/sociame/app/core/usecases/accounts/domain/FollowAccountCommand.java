package com.sociame.app.core.usecases.accounts.domain;

public record FollowAccountCommand(
        Long accountId,
        String username
) {}
