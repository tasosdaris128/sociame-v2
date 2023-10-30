package com.sociame.app.core.usecases.posts.application.ports.in;

import com.sociame.app.core.usecases.posts.domain.CreatePostCommand;
import com.sociame.app.core.usecases.posts.domain.responses.CreatePostResponse;
import com.sociame.app.core.usecases.posts.domain.responses.PostResponse;

import java.util.Optional;

public interface CreatePostUseCase {

    CreatePostResponse handleCommand(CreatePostCommand command);

}
