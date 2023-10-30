package com.sociame.app.core.usecases.posts.domain.commands;

public record CreateCommentCommand(
        long postId,
        String body,
        String username
) {}
