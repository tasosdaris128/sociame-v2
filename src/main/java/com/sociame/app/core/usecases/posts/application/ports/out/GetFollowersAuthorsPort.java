package com.sociame.app.core.usecases.posts.application.ports.out;

import com.sociame.app.core.usecases.posts.domain.Author;
import com.sociame.app.core.usecases.posts.domain.responses.AuthorResponse;

import java.util.List;

public interface GetFollowersAuthorsPort {

    List<AuthorResponse> getFollowers(long authorId);

}
