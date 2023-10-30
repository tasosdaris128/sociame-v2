package com.sociame.app.core.usecases.posts.application;

import com.sociame.app.core.usecases.posts.application.ports.in.GetCommentsUseCase;
import com.sociame.app.core.usecases.posts.application.ports.out.GetCommentsPort;
import com.sociame.app.core.usecases.posts.domain.GetCommentsCommand;
import com.sociame.app.core.usecases.posts.domain.responses.CommentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GetCommentsService implements GetCommentsUseCase {

    private final GetCommentsPort port;

    @Override
    public List<CommentResponse> handleCommand(GetCommentsCommand command) {
        return port.getComments(command.postId()).stream().map(CommentResponse::map).toList();
    }

}
