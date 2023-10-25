package com.sociame.app.core.usecases.accounts.domain;

public record SetupPasswordCommand (
    String verificationToken,
    int pin,
    String password
) {}
