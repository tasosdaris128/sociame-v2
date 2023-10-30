package com.sociame.app.core.usecases.posts.domain.commands;

public record CreatePostCommand(
        String title,
        String body,
        String username
) {}
