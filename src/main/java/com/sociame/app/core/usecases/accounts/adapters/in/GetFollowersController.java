package com.sociame.app.core.usecases.accounts.adapters.in;

import com.sociame.app.core.usecases.accounts.application.ports.in.GetFollowersUseCase;
import com.sociame.app.core.usecases.accounts.domain.Follower;
import com.sociame.app.core.usecases.accounts.domain.GetFollowersCommand;
import com.sociame.app.core.usecases.accounts.domain.GetFollowersResponse;
import com.sociame.app.core.usecases.users.domain.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class GetFollowersController {

    private final GetFollowersUseCase useCase;

    @GetMapping("/api/auth/account/followers")
    public GetFollowersDTO getFollowers(Authentication authentication) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();

        log.info("Request principal: {}", principal);

        GetFollowersResponse response = useCase.handleCommand(new GetFollowersCommand(principal.getUsername()));

        return new GetFollowersDTO(response.followers());
    }

    public record GetFollowersDTO(List<Follower> followers) {}

}
