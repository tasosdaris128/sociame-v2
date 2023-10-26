package com.sociame.app.core.usecases.posts.domain;

public record Author(
        Long id,
        String firstName,
        String lastName,
        int plan
) {
    public boolean isFree() {
        return (this.plan == 1);
    }

    public boolean isPremium() {
        return (this.plan == 2);
    }
}
