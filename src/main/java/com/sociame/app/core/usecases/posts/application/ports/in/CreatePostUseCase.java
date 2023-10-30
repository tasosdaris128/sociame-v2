package com.sociame.app.core.usecases.posts.application.ports.in;

import com.sociame.app.core.usecases.posts.domain.commands.CreatePostCommand;
import com.sociame.app.core.usecases.posts.domain.responses.CreatePostResponse;

public interface CreatePostUseCase {

    CreatePostResponse handleCommand(CreatePostCommand command);

}
