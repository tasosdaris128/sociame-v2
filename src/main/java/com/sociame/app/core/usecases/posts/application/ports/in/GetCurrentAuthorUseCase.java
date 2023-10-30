package com.sociame.app.core.usecases.posts.application.ports.in;

import com.sociame.app.core.usecases.posts.domain.Author;

import java.util.Optional;

public interface GetCurrentAuthorUseCase {

    Optional<Author> getCurrentAuthor(String username);

}
