package com.sociame.app.core.usecases.posts.application.ports.out;

import com.sociame.app.core.usecases.posts.domain.Post;

import java.util.Optional;

public interface CreatePostPort {

    Optional<Post> createPost(Post post);

}
