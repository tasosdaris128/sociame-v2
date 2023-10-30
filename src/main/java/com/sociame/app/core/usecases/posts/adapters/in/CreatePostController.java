package com.sociame.app.core.usecases.posts.adapters.in;

import com.sociame.app.core.usecases.posts.application.ports.in.CreatePostUseCase;
import com.sociame.app.core.usecases.posts.domain.commands.CreatePostCommand;
import com.sociame.app.core.usecases.posts.domain.responses.CreatePostResponse;
import com.sociame.app.core.usecases.users.domain.UserDetailsImpl;
import com.sociame.app.core.usecases.utils.annotations.PostMappingJSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CreatePostController {

    private final CreatePostUseCase useCase;

    @PostMappingJSON("/api/auth/post")
    public CreatePostResponse createPost(
            Authentication authentication,
            @RequestBody CreatePostDTO request
    ) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();

        log.info("Request principal: {}", principal);
        log.info("Requested post: {}", request);

        return useCase.handleCommand(
                new CreatePostCommand(
                        request.title(),
                        request.body(),
                        principal.getUsername()
                )
        );
    }

    public record CreatePostDTO(
            String title,
            String body
    ) {}

}
