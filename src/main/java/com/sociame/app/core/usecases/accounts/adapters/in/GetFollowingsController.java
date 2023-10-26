package com.sociame.app.core.usecases.accounts.adapters.in;

import com.sociame.app.core.usecases.accounts.application.ports.in.GetFollowingsUseCase;
import com.sociame.app.core.usecases.accounts.domain.Following;
import com.sociame.app.core.usecases.accounts.domain.GetFollowingsCommand;
import com.sociame.app.core.usecases.accounts.domain.GetFollowingsResponse;
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
public class GetFollowingsController {

    private final GetFollowingsUseCase useCase;

    @GetMapping("/api/auth/account/followings")
    public GetFollowingsDTO getFollowings(Authentication authentication) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();

        log.info("Request principal: {}", principal);

        GetFollowingsResponse response = useCase.handleCommand(new GetFollowingsCommand(principal.getUsername()));

        return new GetFollowingsDTO(response.followings());
    }

    public record GetFollowingsDTO(List<Following> followings) {}

}
