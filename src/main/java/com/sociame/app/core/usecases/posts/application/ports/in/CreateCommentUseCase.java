package com.sociame.app.core.usecases.posts.application.ports.in;

import com.sociame.app.core.usecases.posts.domain.CreateCommentCommand;
import com.sociame.app.core.usecases.posts.domain.responses.CommentResponse;
import com.sociame.app.core.usecases.posts.domain.responses.CreateCommentResponse;

import java.util.Optional;

public interface CreateCommentUseCase {

    CreateCommentResponse handleCommand(CreateCommentCommand command);

}
