package com.sociame.app.core.usecases.accounts.adapters.in;

import com.sociame.app.core.usecases.accounts.application.ports.in.FollowAccountUseCase;
import com.sociame.app.core.usecases.accounts.domain.FollowAccountCommand;
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
public class FollowAccountController {

    private final FollowAccountUseCase useCase;

    @PostMappingJSON("/api/auth/account/follow/{accountId}")
    public ResponseEntity<Void> follow(
            Authentication authentication,
            @PathVariable("accountId") long accountId) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();

        boolean result = useCase.handleCommand(new FollowAccountCommand(
                accountId,
                principal.getUsername()
        ));

        if (!result) return ResponseEntity.badRequest().build();

        log.info("Request principal: {}", principal);
        log.info("Account to follow: {}", accountId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
