package com.sociame.app.core.usecases.posts.application.ports.out;

import com.sociame.app.core.usecases.posts.domain.Author;
import com.sociame.app.core.usecases.posts.domain.Post;

import java.util.List;

public interface GetOwnPostsPort {

    List<Post> getOwnPosts(Author author);

}
