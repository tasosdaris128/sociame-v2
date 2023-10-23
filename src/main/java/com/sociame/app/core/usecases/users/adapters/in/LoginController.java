package com.sociame.app.core.usecases.users.adapters.in;

import com.sociame.app.core.usecases.users.application.ports.in.LoginUseCase;
import com.sociame.app.core.usecases.users.domain.LoginCommand;
import com.sociame.app.core.usecases.users.domain.LoginResponse;
import com.sociame.app.core.usecases.utils.annotations.PostMappingJSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginUseCase loginUseCase;

    @PostMappingJSON("/token/login")
    LoginResponseDTO login(@RequestBody LoginDTO login) {
        LoginCommand command = new LoginCommand(login.username(), login.password());
        LoginResponse response = loginUseCase.handleCommand(command);
        return new LoginResponseDTO(response.token());
    }

    public record LoginDTO(String username, String password) {}

    public record LoginResponseDTO(String token) {}

}
