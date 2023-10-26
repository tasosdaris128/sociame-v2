package com.sociame.app.core.usecases.posts.application.ports.out;

import com.sociame.app.core.usecases.posts.domain.Author;

import java.util.List;

public interface GetFollowersAuthorsPort {

    List<Author> getFollowers(long authorId);

}
