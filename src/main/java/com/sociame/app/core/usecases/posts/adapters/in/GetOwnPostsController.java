package com.sociame.app.core.usecases.posts.adapters.in;

import com.sociame.app.core.usecases.posts.application.ports.in.GetOwnPostsUseCase;
import com.sociame.app.core.usecases.posts.domain.commands.GetOwnPostsCommand;
import com.sociame.app.core.usecases.posts.domain.responses.GetAuthorPostsResponse;
import com.sociame.app.core.usecases.users.domain.UserDetailsImpl;
import com.sociame.app.core.usecases.utils.annotations.GetMappingJSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class GetOwnPostsController {

    private final GetOwnPostsUseCase useCase;

    @GetMappingJSON("/api/auth/posts/own")
    public GetAuthorPostsResponse getOwnPosts(Authentication authentication) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();

        log.info("Request principal: {}", principal);

        return useCase.handleCommand(new GetOwnPostsCommand(principal.getUsername()));
    }

}
