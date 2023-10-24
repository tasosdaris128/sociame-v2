package com.sociame.app.core.usecases.accounts.domain;

import com.sociame.app.core.usecases.users.domain.AccountId;
import lombok.Value;

@Value
public class Account {

    AccountId id;

    UserId userId;

    String firstName;

    String lastName;

    String gender;

    int plan;

    String verificationToken;

    int pin;

    boolean verified;

    public boolean isValidPlan(int candidatePlan) {
        return (candidatePlan <= 2 && candidatePlan >= 1);
    }

    public boolean canUpgrade() {
        return (this.plan < 2);
    }

    public boolean canDowngrade() {
        return (this.plan > 1);
    }

}
