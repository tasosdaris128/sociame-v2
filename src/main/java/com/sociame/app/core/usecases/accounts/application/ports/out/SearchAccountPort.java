package com.sociame.app.core.usecases.accounts.application.ports.out;

import com.sociame.app.core.usecases.accounts.domain.SearchedAccount;

import java.util.List;

public interface SearchAccountPort {

    List<SearchedAccount> searchAccount(String term);

}
