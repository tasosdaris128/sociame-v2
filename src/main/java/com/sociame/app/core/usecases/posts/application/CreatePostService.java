package com.sociame.app.core.usecases.posts.application;

import com.sociame.app.core.usecases.posts.application.ports.in.CreatePostUseCase;
import com.sociame.app.core.usecases.posts.application.ports.out.CreatePostPort;
import com.sociame.app.core.usecases.posts.application.ports.out.GetCurrentAuthorPort;
import com.sociame.app.core.usecases.posts.domain.Author;
import com.sociame.app.core.usecases.posts.domain.Post;
import com.sociame.app.core.usecases.posts.domain.responses.CreatePostCommand;
import com.sociame.app.core.usecases.posts.domain.responses.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreatePostService implements CreatePostUseCase {

    private final CreatePostPort postPort;

    private final GetCurrentAuthorPort authorPort;

    @Override
    public Optional<PostResponse> handleCommand(CreatePostCommand command) {
        Optional<Author> authorOptional = authorPort.getCurrentAuthor(command.username());

        if (authorOptional.isEmpty()) return Optional.empty();

        Author author = authorOptional.get();

        Post post = Post.createPost(command.title(), command.body(), author);

        Optional<Post> postOptional = postPort.createPost(post);

        return postOptional.map(PostResponse::map);

    }
}
