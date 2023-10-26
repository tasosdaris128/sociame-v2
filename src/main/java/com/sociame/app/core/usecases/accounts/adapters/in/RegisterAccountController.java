package com.sociame.app.core.usecases.accounts.adapters.in;

import com.sociame.app.core.usecases.accounts.application.ports.in.RegisterAccountUseCase;
import com.sociame.app.core.usecases.accounts.domain.RegisterAccountCommand;
import com.sociame.app.core.usecases.utils.annotations.PostMappingJSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RegisterAccountController {

    private final RegisterAccountUseCase useCase;

    @PostMappingJSON("/api/public/account/register")
    public ResponseEntity<Void> registerAccount(@RequestBody RegisterAccountDTO request) {
        log.info("Request: {}", request);

        boolean result = useCase.handleCommand(new RegisterAccountCommand(
                request.username().trim(),
                request.firstName().trim(),
                request.lastName().trim(),
                request.gender().trim(),
                request.plan()
        ));

        if (!result) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public record RegisterAccountDTO(
            String username,
            String firstName,
            String lastName,
            String gender,
            int plan
    ) {}

}
