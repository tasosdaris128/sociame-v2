package com.sociame.app.core.usecases.accounts.domain;

public record UpgradeAccountCommand(
        int plan,
        String username
) {}
