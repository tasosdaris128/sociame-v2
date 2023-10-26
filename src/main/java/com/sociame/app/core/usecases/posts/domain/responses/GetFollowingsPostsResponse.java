package com.sociame.app.core.usecases.posts.domain.responses;

import java.util.List;

public record GetFollowingsPostsResponse(
        List<PostResponse> posts
) {}
