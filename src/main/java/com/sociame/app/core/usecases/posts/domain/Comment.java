package com.sociame.app.core.usecases.posts.domain;

public record Comment(
        Long id,
        String body,
        Long postId,
        Author author
) {}
