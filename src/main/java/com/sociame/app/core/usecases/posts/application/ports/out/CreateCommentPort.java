package com.sociame.app.core.usecases.posts.application.ports.out;

import com.sociame.app.core.usecases.posts.domain.Author;
import com.sociame.app.core.usecases.posts.domain.Comment;
import com.sociame.app.core.usecases.posts.domain.responses.CreateCommentResponse;

import java.util.Optional;

public interface CreateCommentPort {

    CreateCommentResponse createComment(long postId, String body, Author author);

    CreateCommentResponse createComment(Comment comment);

}
