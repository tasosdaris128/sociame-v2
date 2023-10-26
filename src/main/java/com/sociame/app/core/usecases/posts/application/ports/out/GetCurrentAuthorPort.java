package com.sociame.app.core.usecases.posts.application.ports.out;

import com.sociame.app.core.usecases.posts.domain.Author;

import java.util.Optional;

public interface GetCurrentAuthorPort {

    Optional<Author> getCurrentAuthor(String username);

}
