package com.sociame.app.core.usecases.posts.domain;

import com.sociame.app.core.usecases.accounts.domain.Account;
import com.sociame.app.core.usecases.accounts.domain.AccountId;
import com.sociame.app.core.usecases.accounts.domain.UserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccountAcceptanceTest {

    static Account freeAccount = new Account(
            new AccountId(1L),
            new UserId(1L),
            "free@test.com",
            "Tester",
            "Tester",
            "M",
            1,
            null,
            0,
            true,
            null,
            null
    );

    static Account premiumAccount = new Account(
            new AccountId(2L),
            new UserId(2L),
            "premium@test.com",
            "Tester",
            "Tester",
            "M",
            2,
            null,
            0,
            true,
            null,
            null
    );

    @DisplayName("A free account can be upgraded.")
    @Test
    void freeAccountCanBeUpgraded() {
        assertTrue(freeAccount.canChangePlan(2), "A free account should be upgradable.");
    }

    @DisplayName("A free account cannot be downgraded.")
    @Test
    void freeAccountCannotBeDowngraded() {
        assertFalse(freeAccount.canChangePlan(1), "A free account should not be downgradable.");
    }

    @DisplayName("A premium account can be downgraded.")
    @Test
    void premiumAccountCanBeDowngraded() {
        assertTrue(premiumAccount.canChangePlan(1), "A premium account should be downgradable.");
    }

    @DisplayName("A premium account cannot be upgraded.")
    @Test
    void premiumAccountCannotBeUpgraded() {
        assertFalse(premiumAccount.canChangePlan(2), "A premium account should not be upgradable.");
    }

}
