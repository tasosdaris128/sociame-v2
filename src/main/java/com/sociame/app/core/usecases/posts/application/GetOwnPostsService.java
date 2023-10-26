package com.sociame.app.core.usecases.posts.application;

import com.sociame.app.core.usecases.posts.application.ports.in.GetOwnPostsUseCase;
import com.sociame.app.core.usecases.posts.application.ports.out.GetCurrentAuthorPort;
import com.sociame.app.core.usecases.posts.application.ports.out.GetOwnPostsPort;
import com.sociame.app.core.usecases.posts.domain.Author;
import com.sociame.app.core.usecases.posts.domain.GetOwnPostsCommand;
import com.sociame.app.core.usecases.posts.domain.Post;
import com.sociame.app.core.usecases.posts.domain.responses.GetOwnPostsResponse;
import com.sociame.app.core.usecases.posts.domain.responses.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetOwnPostsService implements GetOwnPostsUseCase {

    private final GetOwnPostsPort postPort;

    private final GetCurrentAuthorPort authorPort;

    @Override
    public Optional<GetOwnPostsResponse> handleCommand(GetOwnPostsCommand command) {
        Optional<Author> optionalAuthor = authorPort.getCurrentAuthor(command.username());

        if (optionalAuthor.isEmpty()) return Optional.empty();

        Author author = optionalAuthor.get();

        List<Post> post = postPort.getOwnPosts(author);

        if (post.isEmpty()) return Optional.empty();

        List<PostResponse> postResponses = post.stream().map(PostResponse::map).toList();

        return Optional.of(new GetOwnPostsResponse(postResponses));
    }

}
