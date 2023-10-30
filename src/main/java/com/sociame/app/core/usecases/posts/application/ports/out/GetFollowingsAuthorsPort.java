package com.sociame.app.core.usecases.posts.application.ports.out;

import com.sociame.app.core.usecases.posts.domain.responses.AuthorResponse;

import java.util.List;

public interface GetFollowingsAuthorsPort {

    List<AuthorResponse> getFollowings(long authorId);

}
