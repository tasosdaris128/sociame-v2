package com.sociame.app.core.usecases.posts.domain.responses;

import com.sociame.app.core.usecases.posts.domain.Post;
import com.sociame.app.core.usecases.posts.domain.responses.AuthorResponse;
import com.sociame.app.core.usecases.posts.domain.responses.CommentResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public record PostResponse(
        long id,
        String title,
        String body,
        AuthorResponse author,
        List<CommentResponse> comments
) {
    public static PostResponse map(Post post) {

        List<CommentResponse> comments;

        if (post.getComments().isEmpty()) {
            comments = new ArrayList<>();
        } else {
            comments = post.getComments()
                    .stream()
                    .map(CommentResponse::map)
                    .collect(Collectors.toList());
        }

        return new PostResponse(
                post.getPostId().id(),
                post.getTitle(),
                post.getBody(),
                AuthorResponse.map(post.getAuthor()),
                comments
        );
    }
}
