package com.sociame.app.core.usecases.accounts.adapters.in;

import com.sociame.app.core.usecases.accounts.application.ports.in.GetCurrentAccountUseCase;
import com.sociame.app.core.usecases.accounts.domain.GetCurrentAccountCommand;
import com.sociame.app.core.usecases.accounts.domain.GetCurrentAccountResponse;
import com.sociame.app.core.usecases.users.domain.UserDetailsImpl;
import com.sociame.app.core.usecases.utils.annotations.GetMappingJSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class GetCurrentAccountController {

    private final GetCurrentAccountUseCase useCase;

    @GetMappingJSON("/api/auth/account/me")
    public GetCurrentAccountResponse getMyself(Authentication authentication) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();

        log.info("Request principal: {}", principal);

        return useCase.handleCommand(new GetCurrentAccountCommand(principal.getUsername()));
    }

}
