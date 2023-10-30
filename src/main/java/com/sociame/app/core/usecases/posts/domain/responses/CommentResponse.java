package com.sociame.app.core.usecases.posts.domain.responses;

import com.sociame.app.core.usecases.posts.domain.Comment;

public record CommentResponse(
        Long id,
        String body,
        Long postId,
        AuthorResponse author
) {
    public static CommentResponse map(Comment comment) {
        return new CommentResponse(
                comment.id(),
                comment.body(),
                comment.postId(),
                AuthorResponse.map(comment.author())
        );
    }

    public Comment toDomain() {
        return new Comment(
                this.id,
                this.body,
                this.postId,
                this.author.toDomain()
        );
    }
}
