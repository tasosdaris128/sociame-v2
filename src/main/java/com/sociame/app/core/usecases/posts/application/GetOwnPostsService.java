package com.sociame.app.core.usecases.posts.application;

import com.sociame.app.core.usecases.posts.application.ports.in.GetOwnPostsUseCase;
import com.sociame.app.core.usecases.posts.application.ports.out.GetAuthorPostsPort;
import com.sociame.app.core.usecases.posts.application.ports.out.GetCurrentAuthorPort;
import com.sociame.app.core.usecases.posts.domain.Author;
import com.sociame.app.core.usecases.posts.domain.GetOwnPostsCommand;
import com.sociame.app.core.usecases.posts.domain.Post;
import com.sociame.app.core.usecases.posts.domain.responses.GetAuthorPostsResponse;
import com.sociame.app.core.usecases.posts.domain.responses.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetOwnPostsService implements GetOwnPostsUseCase {

    private final GetAuthorPostsPort postPort;

    private final GetCurrentAuthorPort authorPort;

    @Override
    public GetAuthorPostsResponse handleCommand(GetOwnPostsCommand command) {
        Optional<Author> optionalAuthor = authorPort.getCurrentAuthor(command.username());

        if (optionalAuthor.isEmpty()) throw new RuntimeException("Unable to retrieve current author.");

        Author author = optionalAuthor.get();

        List<Post> post = postPort.getAuthorPosts(author);

        if (post.isEmpty()) return new GetAuthorPostsResponse(new ArrayList<>());

        List<PostResponse> postResponses = post.stream().map(PostResponse::map).toList();

        return new GetAuthorPostsResponse(postResponses);
    }

}
