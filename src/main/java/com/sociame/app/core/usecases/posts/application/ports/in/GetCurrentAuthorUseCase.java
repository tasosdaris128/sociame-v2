package com.sociame.app.core.usecases.posts.application.ports.in;

import com.sociame.app.core.usecases.posts.domain.Author;
import com.sociame.app.core.usecases.posts.domain.responses.AuthorResponse;

import java.util.Optional;

public interface GetCurrentAuthorUseCase {

    AuthorResponse getCurrentAuthor(String username);

}
