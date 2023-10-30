package com.sociame.app.core.usecases.posts.application.ports.in;

import com.sociame.app.core.usecases.posts.domain.commands.GetOwnPostsCommand;
import com.sociame.app.core.usecases.posts.domain.responses.GetAuthorPostsResponse;

public interface GetOwnPostsUseCase {

    GetAuthorPostsResponse handleCommand(GetOwnPostsCommand command);

}
