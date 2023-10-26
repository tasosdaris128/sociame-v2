package com.sociame.app.core.usecases.posts.application.ports.in;

import com.sociame.app.core.usecases.posts.domain.GetFollowersPostsCommand;
import com.sociame.app.core.usecases.posts.domain.responses.GetFollowersPostsResponse;

import java.util.Optional;

public interface GetFollowersPostsUseCase {

    Optional<GetFollowersPostsResponse> handleCommand(GetFollowersPostsCommand command);

}
