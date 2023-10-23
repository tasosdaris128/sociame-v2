package com.sociame.app.core.usecases.users.domain;

public record JWTSettings(String issuer, int expirationInMinutes, String secret) {}
