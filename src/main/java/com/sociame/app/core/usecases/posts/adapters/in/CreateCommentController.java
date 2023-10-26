package com.sociame.app.core.usecases.posts.adapters.in;

import com.sociame.app.core.usecases.posts.application.ports.in.CreateCommentUseCase;
import com.sociame.app.core.usecases.posts.domain.CreateCommentCommand;
import com.sociame.app.core.usecases.posts.domain.responses.CommentResponse;
import com.sociame.app.core.usecases.users.domain.UserDetailsImpl;
import com.sociame.app.core.usecases.utils.annotations.PostMappingJSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CreateCommentController {

    private final CreateCommentUseCase useCase;

    @PostMappingJSON("/api/auth/post/comment/{postId}")
    public ResponseEntity<CommentResponse> createComment(
            Authentication authentication,
            @PathVariable(value = "postId") long postId,
            @RequestBody CreateCommentDTO request
    ) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();

        log.info("Request principal: {}", principal);
        log.info("Requested comment: {}", request);

        return ResponseEntity.of(
                useCase.handleCommand(new CreateCommentCommand(
                        postId,
                        request.comment(),
                        principal.getUsername()
                ))
        );
    }

    public record CreateCommentDTO(String comment) {}

}
