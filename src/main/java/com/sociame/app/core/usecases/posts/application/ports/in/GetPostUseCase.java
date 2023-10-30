package com.sociame.app.core.usecases.posts.application.ports.in;

import com.sociame.app.core.usecases.posts.domain.Post;

import java.util.Optional;

public interface GetPostUseCase {

    Optional<Post> getPost(long postId);

}
