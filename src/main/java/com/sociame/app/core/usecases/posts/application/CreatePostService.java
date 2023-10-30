package com.sociame.app.core.usecases.posts.application;

import com.sociame.app.core.usecases.posts.application.ports.in.CreatePostUseCase;
import com.sociame.app.core.usecases.posts.application.ports.in.GetCurrentAuthorUseCase;
import com.sociame.app.core.usecases.posts.application.ports.out.CreatePostPort;
import com.sociame.app.core.usecases.posts.domain.Author;
import com.sociame.app.core.usecases.posts.domain.Post;
import com.sociame.app.core.usecases.posts.domain.commands.CreatePostCommand;
import com.sociame.app.core.usecases.posts.domain.responses.CreatePostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CreatePostService implements CreatePostUseCase {

    private final GetCurrentAuthorUseCase authorUseCase;

    private final CreatePostPort postPort;

    @Override
    public CreatePostResponse handleCommand(CreatePostCommand command) {
        Author author = authorUseCase.getCurrentAuthor(command.username()).toDomain();

        Post post = Post.createPost(command.title(), command.body(), author);

        return postPort.createPost(post);
    }

}
