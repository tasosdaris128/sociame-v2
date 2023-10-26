package com.sociame.app.core.usecases.posts.application.ports.in;

import com.sociame.app.core.usecases.posts.domain.GetFollowingsPostsCommand;
import com.sociame.app.core.usecases.posts.domain.responses.GetFollowingsPostsResponse;

import java.util.Optional;

public interface GetFollowingPostsUseCase {

    Optional<GetFollowingsPostsResponse> handleCommand(GetFollowingsPostsCommand command);

}
