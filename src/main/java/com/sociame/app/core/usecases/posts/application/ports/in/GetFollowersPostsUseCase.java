package com.sociame.app.core.usecases.posts.application.ports.in;

import com.sociame.app.core.usecases.posts.domain.commands.GetFollowersPostsCommand;
import com.sociame.app.core.usecases.posts.domain.responses.GetFollowersPostsResponse;

public interface GetFollowersPostsUseCase {

    GetFollowersPostsResponse handleCommand(GetFollowersPostsCommand command);

}
