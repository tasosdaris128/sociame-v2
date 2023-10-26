package com.sociame.app.core.usecases.accounts.domain;

public record SearchedAccount(
        Long id,

        Long userId,

        String username,

        String firstName,

        String lastName,

        String gender
) {}
