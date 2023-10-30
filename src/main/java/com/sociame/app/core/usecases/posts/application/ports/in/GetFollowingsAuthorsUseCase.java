package com.sociame.app.core.usecases.posts.application.ports.in;

import com.sociame.app.core.usecases.posts.domain.responses.AuthorResponse;

import java.util.List;

public interface GetFollowingsAuthorsUseCase {

    List<AuthorResponse> getFollowings(long authorId);

}
