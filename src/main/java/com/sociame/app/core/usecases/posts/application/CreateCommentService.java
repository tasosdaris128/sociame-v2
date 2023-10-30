package com.sociame.app.core.usecases.posts.application;

import com.sociame.app.config.web.KnownRuntimeError;
import com.sociame.app.core.usecases.posts.application.ports.in.CreateCommentUseCase;
import com.sociame.app.core.usecases.posts.application.ports.in.GetCurrentAuthorUseCase;
import com.sociame.app.core.usecases.posts.application.ports.in.GetPostUseCase;
import com.sociame.app.core.usecases.posts.application.ports.out.CreateCommentPort;
import com.sociame.app.core.usecases.posts.domain.Author;
import com.sociame.app.core.usecases.posts.domain.Comment;
import com.sociame.app.core.usecases.posts.domain.Post;
import com.sociame.app.core.usecases.posts.domain.commands.CreateCommentCommand;
import com.sociame.app.core.usecases.posts.domain.responses.CreateCommentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CreateCommentService implements CreateCommentUseCase {

    private final GetCurrentAuthorUseCase authorUseCase;

    private final GetPostUseCase postUseCase;

    private final CreateCommentPort commentPort;

    @Override
    public CreateCommentResponse handleCommand(CreateCommentCommand command) {
        Author author = authorUseCase.getCurrentAuthor(command.username()).toDomain();

        Post post = postUseCase.getPost(command.postId()).toDomain();

        Comment candidateComment = new Comment(
                0L,
                command.body(),
                command.postId(),
                author
        );

        Comment newComment = post.createComment(candidateComment, author);

        if (newComment == null) throw new KnownRuntimeError("You have exceeded your plan's comment quota.");

        return commentPort.createComment(newComment);
    }

}
