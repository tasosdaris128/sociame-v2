package com.sociame.app.core.usecases.accounts.domain;

import java.util.List;

public record SearchAccountResponse(List<SearchedAccount> results) {}
