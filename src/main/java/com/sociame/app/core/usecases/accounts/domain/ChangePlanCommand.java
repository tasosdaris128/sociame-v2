package com.sociame.app.core.usecases.accounts.domain;

public record ChangePlanCommand(
        int plan,
        String username
) {}
