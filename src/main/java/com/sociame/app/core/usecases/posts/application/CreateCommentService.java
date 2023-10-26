package com.sociame.app.core.usecases.posts.application;

import com.sociame.app.core.usecases.posts.application.ports.in.CreateCommentUseCase;
import com.sociame.app.core.usecases.posts.application.ports.out.CreateCommentPort;
import com.sociame.app.core.usecases.posts.application.ports.out.GetCurrentAuthorPort;
import com.sociame.app.core.usecases.posts.application.ports.out.GetPostPort;
import com.sociame.app.core.usecases.posts.domain.Author;
import com.sociame.app.core.usecases.posts.domain.Comment;
import com.sociame.app.core.usecases.posts.domain.CreateCommentCommand;
import com.sociame.app.core.usecases.posts.domain.Post;
import com.sociame.app.core.usecases.posts.domain.responses.CommentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateCommentService implements CreateCommentUseCase {

    private final GetCurrentAuthorPort authorPort;

    private final CreateCommentPort commentPort;

    private final GetPostPort postPort;

    @Override
    public Optional<CommentResponse> handleCommand(CreateCommentCommand command) {
        Optional<Author> optionalAuthor = authorPort.getCurrentAuthor(command.username());

        if (optionalAuthor.isEmpty()) return Optional.empty();

        Author author = optionalAuthor.get();

        Optional<Post> optionalPost = postPort.getPost(command.postId());

        if (optionalPost.isEmpty()) return Optional.empty();

        Post post = optionalPost.get();

        Comment candidateComment = new Comment(
                0L,
                command.body(),
                command.postId(),
                author
        );

        Comment newComment = post.createComment(candidateComment, author);

        if (newComment == null) return Optional.empty();

        Optional<Comment> optionalComment = commentPort.createComment(newComment);

        if (optionalComment.isEmpty()) return Optional.empty();

        Comment comment = optionalComment.get();

        return Optional.of(CommentResponse.map(comment));
    }

}
