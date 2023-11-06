package com.sociame.app.core.usecases.posts.domain;

import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
public class Post {

    PostId postId;

    String title;

    String body;

    Author author;

    List<Comment> comments;

    public static Post createPost(String title, String body, Author poster) {
        String refinedBody = body;

        truncateBodyBasedOnPlan: {
            if (body.length() <= 1000) break truncateBodyBasedOnPlan;

            if (poster.isFree()) {
                refinedBody = body.substring(0, 1000);
                break truncateBodyBasedOnPlan;
            }

            if (body.length() > 3000) {
                refinedBody = body.substring(0, 3000);
            }
        }

        return new Post(
                new PostId(0L), // id is not available at this point
                title,
                refinedBody,
                poster,
                new ArrayList<>()
        );
    }

    public Comment createComment(Comment newComment, Author commenter) {
        if (this.comments == null || this.comments.isEmpty()) return newComment;

        createCommentBasedOnPlan: {
            long existingCommentsOfAuthor = this.comments.stream()
                    .filter(e -> e.author().id().equals(commenter.id()))
                    .count();

            if (existingCommentsOfAuthor < 5) break createCommentBasedOnPlan;

            if (commenter.isFree()) return null;
        }

        return newComment;
    }

}
