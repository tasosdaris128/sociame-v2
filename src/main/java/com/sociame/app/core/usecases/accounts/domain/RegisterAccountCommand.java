package com.sociame.app.core.usecases.accounts.domain;

public record RegisterAccountCommand(
        String username,
        String firstName,
        String lastName,
        String gender,
        int plan
) {}
