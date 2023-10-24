package com.sociame.app.core.usecases.users.domain;

public record GetUserAccountResponse(
        Long accountId,
        Long userId,
        String username,
        String firstName,
        String lastName,
        String gender,
        int plan,
        boolean verified
) {}
