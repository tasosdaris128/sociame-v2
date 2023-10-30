package com.sociame.app.core.usecases.posts.application.ports.in;

import com.sociame.app.core.usecases.posts.domain.responses.AuthorResponse;
import com.sociame.app.core.usecases.posts.domain.responses.PostResponse;

import java.util.List;

public interface GetAuthorPostsUseCase {

    List<PostResponse> getAuthorPosts(AuthorResponse author);

}
