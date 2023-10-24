package com.sociame.app.core.usecases.users.domain;

import com.sociame.app.core.usecases.utils.SelfValidating;

public record GetUserAccountCommand(String username) implements SelfValidating<GetUserAccountCommand> {
    public GetUserAccountCommand(String username) {
        this.username = username;
        this.seldValidate();
    }
}
