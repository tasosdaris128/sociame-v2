package com.sociame.app.core.usecases.posts.application.ports.in;

import com.sociame.app.core.usecases.posts.domain.GetOwnPostsCommand;
import com.sociame.app.core.usecases.posts.domain.responses.GetOwnPostsResponse;

import java.util.Optional;

public interface GetOwnPostsUseCase {

    Optional<GetOwnPostsResponse> handleCommand(GetOwnPostsCommand command);

}
