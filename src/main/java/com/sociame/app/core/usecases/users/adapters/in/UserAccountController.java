package com.sociame.app.core.usecases.users.adapters.in;

import com.sociame.app.core.usecases.users.application.ports.in.GetUserAccountUseCase;
import com.sociame.app.core.usecases.users.domain.GetUserAccountCommand;
import com.sociame.app.core.usecases.users.domain.GetUserAccountResponse;
import com.sociame.app.core.usecases.users.domain.UserDetailsImpl;
import com.sociame.app.core.usecases.utils.annotations.GetMappingJSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserAccountController {

    private final GetUserAccountUseCase userCase;

    @GetMappingJSON("/api/me")
    public UserAccountResponseDTO getUserAccount(Authentication authentication) {
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        GetUserAccountCommand command = new GetUserAccountCommand(user.getUsername());
        GetUserAccountResponse response = userCase.handleCommand(command);

        return new UserAccountResponseDTO(
                response.accountId(),
                response.userId(),
                response.username(),
                response.firstName(),
                response.lastName(),
                response.gender(),
                response.plan(),
                response.verified()
        );
    }

    public record UserAccountResponseDTO(
            Long accountId,
            Long userId,
            String username,
            String firstName,
            String lastName,
            String gender,
            int plan,
            boolean verified
    ) {}

}
