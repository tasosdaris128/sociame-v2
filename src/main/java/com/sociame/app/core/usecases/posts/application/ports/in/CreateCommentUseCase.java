package com.sociame.app.core.usecases.posts.application.ports.in;

import com.sociame.app.core.usecases.posts.domain.commands.CreateCommentCommand;
import com.sociame.app.core.usecases.posts.domain.responses.CreateCommentResponse;

public interface CreateCommentUseCase {

    CreateCommentResponse handleCommand(CreateCommentCommand command);

}
