package com.sociame.app.core.usecases.posts.domain;

public record CreateCommentCommand(
        long postId,
        String body,
        String username
) {}
