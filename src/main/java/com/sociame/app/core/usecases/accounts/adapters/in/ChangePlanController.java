package com.sociame.app.core.usecases.accounts.adapters.in;

import com.sociame.app.core.usecases.accounts.application.ports.in.ChangePlanUseCase;
import com.sociame.app.core.usecases.accounts.domain.ChangePlanCommand;
import com.sociame.app.core.usecases.accounts.domain.ChangePlanResponse;
import com.sociame.app.core.usecases.users.domain.UserDetailsImpl;
import com.sociame.app.core.usecases.utils.annotations.PutMappingJSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChangePlanController {

    private final ChangePlanUseCase useCase;

    @PutMappingJSON("/api/auth/account/plan/{planId}")
    public ResponseEntity<AccountResponseDTO> changePlan(
            Authentication authentication,
            @PathVariable(value = "planId") int plan
    ) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();

        log.info("Request principal: {}", principal);
        log.info("Requested plan: {}", plan);

        Optional<ChangePlanResponse> response = useCase.handleCommand(new ChangePlanCommand(
                plan,
                principal.getUsername()
        ));

        if (response.isEmpty()) return ResponseEntity.badRequest().build();

        ChangePlanResponse account = response.get();

        return ResponseEntity.ok(new AccountResponseDTO(
                account.id(),
                account.userId(),
                account.username(),
                account.firstName(),
                account.lastName(),
                account.gender(),
                account.plan()
        ));
    }

    public record AccountResponseDTO(
            Long accountId,
            Long userId,
            String username,
            String firstName,
            String lastName,
            String gender,
            int plan
    ) {}

}
