package com.sociame.app.core.usecases.accounts.domain;

import lombok.Value;

@Value
public class Account {

    AccountId id;

    UserId userId;

    String username;

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

    public boolean isNotValidPlan(int candidatePlan) {
        return !isValidPlan(candidatePlan);
    }

    @Deprecated
    public boolean canUpgrade() {
        return (this.plan < 2);
    }

    @Deprecated
    public boolean canDowngrade() {
        return (this.plan > 1);
    }

    public boolean isFree() {
        return (this.plan == 1);
    }

    public boolean isPremium() {
        return (this.plan == 2);
    }

    public boolean canChangePlan(int newPlan) {
        if (isNotValidPlan(newPlan)) return false;

        if (isFree() && (newPlan > this.plan)) return true;

        return isPremium() && newPlan < this.plan;
    }

    public boolean cannotChangePlan(int newPlan) {
        return !canChangePlan(newPlan);
    }

}
