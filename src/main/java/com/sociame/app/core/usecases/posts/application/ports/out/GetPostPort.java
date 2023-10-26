package com.sociame.app.core.usecases.posts.application.ports.out;

import com.sociame.app.core.usecases.posts.domain.Post;

import java.util.Optional;

public interface GetPostPort {

    Optional<Post> getPost(long postId);

}
