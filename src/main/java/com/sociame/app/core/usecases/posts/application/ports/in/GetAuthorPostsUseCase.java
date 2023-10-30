package com.sociame.app.core.usecases.posts.application.ports.in;

import com.sociame.app.core.usecases.posts.domain.Author;
import com.sociame.app.core.usecases.posts.domain.Post;

import java.util.List;

public interface GetAuthorPostsUseCase {

    List<Post> getAuthorPosts(Author author);

}
