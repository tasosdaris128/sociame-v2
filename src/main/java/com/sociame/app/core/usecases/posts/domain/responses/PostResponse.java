package com.sociame.app.core.usecases.posts.domain.responses;

import com.sociame.app.core.usecases.posts.domain.Comment;
import com.sociame.app.core.usecases.posts.domain.Post;
import com.sociame.app.core.usecases.posts.domain.PostId;

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

    public Post toDomain() {
        List<Comment> comments;

        if (this.comments.isEmpty()) {
            comments = new ArrayList<>();
        } else {
            comments = this.comments.stream()
                    .map(CommentResponse::toDomain)
                    .toList();
        }

        return new Post(
                new PostId(this.id),
                this.title,
                this.body,
                this.author.toDomain(),
                comments
        );
    }
}
