package com.sociame.app.core.usecases.posts.application;

import com.sociame.app.config.web.KnownRuntimeError;
import com.sociame.app.core.usecases.posts.application.ports.in.CreateCommentUseCase;
import com.sociame.app.core.usecases.posts.application.ports.in.GetCurrentAuthorUseCase;
import com.sociame.app.core.usecases.posts.application.ports.in.GetPostUseCase;
import com.sociame.app.core.usecases.posts.application.ports.out.CreateCommentPort;
import com.sociame.app.core.usecases.posts.domain.Author;
import com.sociame.app.core.usecases.posts.domain.Comment;
import com.sociame.app.core.usecases.posts.domain.CreateCommentCommand;
import com.sociame.app.core.usecases.posts.domain.Post;
import com.sociame.app.core.usecases.posts.domain.responses.CreateCommentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
        Optional<Author> optionalAuthor = authorUseCase.getCurrentAuthor(command.username());

        if (optionalAuthor.isEmpty()) {
            throw new KnownRuntimeError("Unable to retrieve current author.");
        }

        Author author = optionalAuthor.get();

        Optional<Post> optionalPost = postUseCase.getPost(command.postId());

        if (optionalPost.isEmpty()) {
            throw new KnownRuntimeError("Unable to retrieve post.");
        }

        Post post = optionalPost.get();

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
