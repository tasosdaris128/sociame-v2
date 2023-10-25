package com.sociame.app.core.usecases.accounts.adapters.in;

import com.sociame.app.core.usecases.accounts.application.ports.in.SetupPasswordUseCase;
import com.sociame.app.core.usecases.accounts.domain.SetupPasswordCommand;
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
public class SetupPasswordController {

    private final SetupPasswordUseCase useCase;

    @PostMappingJSON("/api/public/account/password/setup")
    public ResponseEntity<Void> setupPassword(@RequestBody SetupPasswordDTO request) {
        log.info("Request: {}", request);

        boolean result = useCase.handleCommand(new SetupPasswordCommand(
                request.verificationToken(),
                request.pin(),
                request.password()
        ));

        if (!result) return ResponseEntity.badRequest().build();

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public record SetupPasswordDTO(
            String verificationToken,
            int pin,
            String password
    ) {}

}
