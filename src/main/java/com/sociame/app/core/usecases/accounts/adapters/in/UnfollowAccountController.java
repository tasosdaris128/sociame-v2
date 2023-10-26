package com.sociame.app.core.usecases.accounts.adapters.in;

import com.sociame.app.core.usecases.accounts.application.ports.in.UnfollowAccountUseCase;
import com.sociame.app.core.usecases.accounts.domain.UnfollowAccountCommand;
import com.sociame.app.core.usecases.users.domain.UserDetailsImpl;
import com.sociame.app.core.usecases.utils.annotations.PostMappingJSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UnfollowAccountController {

    private final UnfollowAccountUseCase useCase;

    @PostMappingJSON("/api/auth/account/unfollow/{accountId}")
    public ResponseEntity<Void> unfollow(
            Authentication authentication,
            @PathVariable("accountId") long accountId) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();

        boolean result = useCase.handleCommand(new UnfollowAccountCommand(
                accountId,
                principal.getUsername()
        ));

        if (!result) return ResponseEntity.badRequest().build();

        log.info("Request principal: {}", principal);
        log.info("Account to unfollow: {}", accountId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
