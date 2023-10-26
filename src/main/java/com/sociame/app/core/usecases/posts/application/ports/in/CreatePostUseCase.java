package com.sociame.app.core.usecases.posts.application.ports.in;

import com.sociame.app.core.usecases.posts.domain.responses.CreatePostCommand;
import com.sociame.app.core.usecases.posts.domain.responses.PostResponse;

import java.util.Optional;

public interface CreatePostUseCase {

    Optional<PostResponse> handleCommand(CreatePostCommand command);

}
