package com.sociame.app.core.usecases.posts.domain;

public record CreatePostCommand(
        String title,
        String body,
        String username
) {}
