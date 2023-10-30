package com.sociame.app.core.usecases.posts.adapters.in;

import com.sociame.app.core.usecases.posts.application.ports.in.GetCommentsUseCase;
import com.sociame.app.core.usecases.posts.domain.commands.GetCommentsCommand;
import com.sociame.app.core.usecases.posts.domain.responses.CommentResponse;
import com.sociame.app.core.usecases.utils.annotations.GetMappingJSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class GetCommentsController {

    private final GetCommentsUseCase useCase;

    @GetMappingJSON("/api/auth/posts/comments/{postId}")
    public List<CommentResponse> getComments(@PathVariable("postId") long postId) {
        return useCase.handleCommand(new GetCommentsCommand(postId));
    }

}
