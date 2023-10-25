package com.sociame.app.core.usecases.accounts.domain;

public record UnfollowAccountCommand(
        Long accountId,
        String username
) {}
