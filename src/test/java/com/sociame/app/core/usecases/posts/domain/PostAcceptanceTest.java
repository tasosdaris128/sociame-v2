package com.sociame.app.core.usecases.posts.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

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

}
