package com.sociame.app.core.usecases.posts.application.ports.out;

import com.sociame.app.core.usecases.posts.domain.Post;
import com.sociame.app.core.usecases.posts.domain.responses.PostResponse;

import java.util.Optional;

public interface GetPostPort {

    PostResponse getPost(long postId);

}
