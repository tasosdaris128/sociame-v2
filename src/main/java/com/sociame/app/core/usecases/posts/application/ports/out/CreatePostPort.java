package com.sociame.app.core.usecases.posts.application.ports.out;

import com.sociame.app.core.usecases.posts.domain.Post;
import com.sociame.app.core.usecases.posts.domain.responses.CreatePostResponse;

import java.util.Optional;

public interface CreatePostPort {

    CreatePostResponse createPost(Post post);

}
