package com.sociame.app.core.usecases.posts.domain.responses;

import java.util.List;

public record GetAuthorPostsResponse(
        List<PostResponse> posts
) {}
