package com.sociame.app.core.usecases.posts.application.ports.in;

import com.sociame.app.core.usecases.posts.domain.GetFollowingsPostsCommand;
import com.sociame.app.core.usecases.posts.domain.responses.GetFollowingsPostsResponse;

public interface GetFollowingPostsUseCase {

    GetFollowingsPostsResponse handleCommand(GetFollowingsPostsCommand command);

}
