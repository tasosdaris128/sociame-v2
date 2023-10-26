package com.sociame.app.core.usecases.posts.domain.responses;

public record CreatePostCommand(
        String title,
        String body,
        String username
) {}
