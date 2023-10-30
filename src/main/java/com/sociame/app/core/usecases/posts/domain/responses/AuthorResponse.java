package com.sociame.app.core.usecases.posts.domain.responses;

import com.sociame.app.core.usecases.posts.domain.Author;

public record AuthorResponse(
        Long id,
        String username,
        String firstName,
        String lastName,
        int plan
) {
    public static AuthorResponse map(Author author) {
        return new AuthorResponse(
                author.id(),
                author.username(),
                author.firstName(),
                author.lastName(),
                author.plan()
        );
    }

    public Author toDomain() {
        return new Author(
                this.id,
                this.username,
                this.firstName,
                this.lastName,
                this.plan
        );
    }
}
