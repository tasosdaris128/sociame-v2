package com.sociame.app.core.usecases.posts.application.ports.in;

import com.sociame.app.core.usecases.posts.domain.commands.GetCommentsCommand;
import com.sociame.app.core.usecases.posts.domain.responses.CommentResponse;

import java.util.List;

public interface GetCommentsUseCase {

    List<CommentResponse> handleCommand(GetCommentsCommand command);

}
