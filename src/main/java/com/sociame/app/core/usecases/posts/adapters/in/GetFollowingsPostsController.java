package com.sociame.app.core.usecases.posts.adapters.in;

import com.sociame.app.core.usecases.posts.application.ports.in.GetFollowingPostsUseCase;
import com.sociame.app.core.usecases.posts.domain.GetFollowingsPostsCommand;
import com.sociame.app.core.usecases.posts.domain.responses.GetFollowingsPostsResponse;
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
public class GetFollowingsPostsController {

    private final GetFollowingPostsUseCase useCase;

    @GetMappingJSON("/api/auth/posts/followings")
    public ResponseEntity<GetFollowingsPostsResponse> getFollowingsPosts(Authentication authentication) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();

        log.info("Request principal for followings: {}", principal);

        return ResponseEntity.of(useCase.handleCommand(new GetFollowingsPostsCommand(principal.getUsername())));
    }

}
