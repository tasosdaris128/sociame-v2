package com.sociame.app.core.usecases.posts.application.ports.in;

import com.sociame.app.core.usecases.posts.domain.Post;
import com.sociame.app.core.usecases.posts.domain.responses.PostResponse;

import java.util.Optional;

public interface GetPostUseCase {

    PostResponse getPost(long postId);

}
