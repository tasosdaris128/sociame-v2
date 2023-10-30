package com.sociame.app.core.usecases.posts.application.ports.in;

import com.sociame.app.core.usecases.posts.domain.Author;

import java.util.List;

public interface GetFollowersAuthorsUseCase {

    List<Author> getFollowers(long authorId);

}
