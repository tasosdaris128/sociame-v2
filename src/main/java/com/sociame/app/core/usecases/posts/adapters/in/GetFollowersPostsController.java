package com.sociame.app.core.usecases.posts.adapters.in;

import com.sociame.app.core.usecases.posts.application.ports.in.GetFollowersPostsUseCase;
import com.sociame.app.core.usecases.posts.domain.GetFollowersPostsCommand;
import com.sociame.app.core.usecases.posts.domain.responses.GetFollowersPostsResponse;
import com.sociame.app.core.usecases.users.domain.UserDetailsImpl;
import com.sociame.app.core.usecases.utils.annotations.GetMappingJSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class GetFollowersPostsController {

    private final GetFollowersPostsUseCase useCase;

    @GetMappingJSON("/api/auth/posts/followers")
    public ResponseEntity<GetFollowersPostsResponse> getFollowersPosts(Authentication authentication) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();

        log.info("Request principal: {}", principal);

        return ResponseEntity.of(useCase.handleCommand(new GetFollowersPostsCommand(principal.getUsername())));
    }

}
