package com.sociame.app.core.usecases.posts.application.ports.out;

import com.sociame.app.core.usecases.posts.domain.Author;
import com.sociame.app.core.usecases.posts.domain.Post;
import com.sociame.app.core.usecases.posts.domain.responses.AuthorResponse;
import com.sociame.app.core.usecases.posts.domain.responses.PostResponse;

import java.util.List;

public interface GetAuthorPostsPort {

    List<PostResponse> getAuthorPosts(AuthorResponse author);

}
