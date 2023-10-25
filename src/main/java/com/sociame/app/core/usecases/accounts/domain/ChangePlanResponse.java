package com.sociame.app.core.usecases.accounts.domain;

public record ChangePlanResponse(
        long id,

        long userId,

        String username,

        String firstName,

        String lastName,

        String gender,

        int plan
) {}
