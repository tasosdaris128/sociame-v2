package com.sociame.app.core.usecases.users.application;

import com.sociame.app.core.usecases.users.adapters.out.UserAdapter;
import com.sociame.app.core.usecases.users.application.ports.in.LoginUseCase;
import com.sociame.app.core.usecases.users.domain.LoginCommand;
import com.sociame.app.core.usecases.users.domain.LoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService implements LoginUseCase {

    private final UserAdapter dbAdapter;

    private final JWTLocalTokenService tokenService;

    @Override
    public LoginResponse handleCommand(LoginCommand command) {
        final UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(command.username(), command.password());

        Optional<UserDetails> user = dbAdapter.loadUserByUsernameAndPassword(command.username(), command.password());

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("");
        }

        final String jwtToken = tokenService.createToken(auth);
        return new LoginResponse(jwtToken);
    }
}
