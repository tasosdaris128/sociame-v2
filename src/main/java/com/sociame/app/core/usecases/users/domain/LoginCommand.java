package com.sociame.app.core.usecases.users.domain;

import com.sociame.app.core.usecases.utils.SelfValidating;

public record LoginCommand(String username, String password) implements SelfValidating<LoginCommand> {
    public LoginCommand(String username, String password) {
        this.username = username;
        this.password = password;
        this.seldValidate();
    }
}
