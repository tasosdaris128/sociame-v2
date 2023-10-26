package com.sociame.app.core.usecases.posts.application.ports.out;

import com.sociame.app.core.usecases.posts.domain.Comment;

import java.util.List;

public interface GetCommentsPort {

    List<Comment> getComments(long postId);

}
