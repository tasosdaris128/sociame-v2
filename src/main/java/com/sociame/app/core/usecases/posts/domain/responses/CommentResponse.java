package com.sociame.app.core.usecases.posts.domain.responses;

import com.sociame.app.core.usecases.posts.domain.Comment;
import com.sociame.app.core.usecases.posts.domain.responses.AuthorResponse;

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
}
