package com.sociame.app.core.usecases.posts.application.ports.out;

import com.sociame.app.core.usecases.posts.domain.Author;
import com.sociame.app.core.usecases.posts.domain.Comment;

import java.util.Optional;

public interface CreateCommentPort {

    Optional<Comment> createComment(long postId, String body, Author author);

    Optional<Comment> createComment(Comment comment);

}
