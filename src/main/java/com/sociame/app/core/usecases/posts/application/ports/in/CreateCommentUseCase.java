package com.sociame.app.core.usecases.posts.application.ports.in;

import com.sociame.app.core.usecases.posts.domain.CreateCommentCommand;
import com.sociame.app.core.usecases.posts.domain.responses.CommentResponse;

import java.util.Optional;

public interface CreateCommentUseCase {

    Optional<CommentResponse> handleCommand(CreateCommentCommand command);

}
