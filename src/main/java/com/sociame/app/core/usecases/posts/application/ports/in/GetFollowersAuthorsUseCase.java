package com.sociame.app.core.usecases.posts.application.ports.in;

import com.sociame.app.core.usecases.posts.domain.Author;
import com.sociame.app.core.usecases.posts.domain.responses.AuthorResponse;

import java.util.List;

public interface GetFollowersAuthorsUseCase {

    List<AuthorResponse> getFollowers(long authorId);

}
