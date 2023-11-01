package com.sociame.app.core.usecases.posts.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PostAcceptanceTest {

    static Post postWithComments;

    static Author authorFreePlan = new Author(
            1L,
            "free@test.com",
            "Tester",
            "Tester",
            1
    );;

    static Author authorPremiumPlan = new Author(
            2L,
            "premium@test.com",
            "Tester",
            "Tester",
            2
    );

    @BeforeAll
    static void setup() {
        List<Comment> comments = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            comments.add(new Comment(
                    (long) i,
                    "A comment",
                    1L,
                    authorFreePlan
            ));
        }

        for (int i = 0; i < 6; i++) {
            comments.add(new Comment(
                    (long) i,
                    "A comment",
                    1L,
                    authorPremiumPlan
            ));
        }

        postWithComments = new Post(
                new PostId(1L),
                "This is a title",
                "This is a body",
                authorFreePlan, // It doesn't matter
                comments
        );
    }

    @DisplayName("A free account cannot comment more that five times.")
    @Test
    void freePlanCannotCommentMoreThanFiveTimes() {
        Comment newComment = postWithComments.createComment(new Comment(
                1L,
                "Comment",
                postWithComments.getPostId().id(),
                authorFreePlan
        ), authorFreePlan);

        assertNull(newComment, "An author with free plan cannot comment more than five times");
    }

    @DisplayName("A premium account can comment more that five times.")
    @Test
    void premiumPlanCanCommentMoreThanFiveTimes() {
        Comment newComment = postWithComments.createComment(new Comment(
                1L,
                "Comment",
                postWithComments.getPostId().id(),
                authorPremiumPlan
        ), authorPremiumPlan);

        assertNotNull(newComment, "An author with premium plan can comment more than five times");
    }

    @DisplayName("A free account cannot create a post with more than 3000 characters.")
    @Test
    void freePlanCannotCreatePostWithMoreThan1000Characters() {
        Post post = Post.createPost("Title", "A".repeat(2000), authorFreePlan);

        assertEquals(1000, post.getBody().length(), "");
    }

    @DisplayName("A premium account cannot create a post with more than 5000 characters.")
    @Test
    void premiumPlanCannotCreatePostWithMoreThan3000Characters() {
        Post post = Post.createPost("Title", "A".repeat(4000), authorPremiumPlan);

        assertEquals(3000, post.getBody().length(), "");
    }

}
