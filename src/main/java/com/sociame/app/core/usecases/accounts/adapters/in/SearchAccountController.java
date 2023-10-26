package com.sociame.app.core.usecases.accounts.adapters.in;

import com.sociame.app.core.usecases.accounts.application.ports.in.SearchAccountUseCase;
import com.sociame.app.core.usecases.accounts.domain.SearchAccountCommand;
import com.sociame.app.core.usecases.accounts.domain.SearchAccountResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SearchAccountController {

    private final SearchAccountUseCase useCase;

    @PostMapping("/api/auth/account/search")
    public SearchAccountResponse search(@RequestBody SearchTermDTO searchTerm) {
        return useCase.handleCommand(new SearchAccountCommand(searchTerm.term().trim()));
    }

    public record SearchTermDTO(String term) {}

}
