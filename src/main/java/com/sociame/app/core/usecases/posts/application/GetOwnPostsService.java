package com.sociame.app.core.usecases.posts.application;

import com.sociame.app.core.usecases.posts.application.ports.in.GetAuthorPostsUseCase;
import com.sociame.app.core.usecases.posts.application.ports.in.GetCurrentAuthorUseCase;
import com.sociame.app.core.usecases.posts.application.ports.in.GetOwnPostsUseCase;
import com.sociame.app.core.usecases.posts.domain.Author;
import com.sociame.app.core.usecases.posts.domain.commands.GetOwnPostsCommand;
import com.sociame.app.core.usecases.posts.domain.responses.AuthorResponse;
import com.sociame.app.core.usecases.posts.domain.responses.GetAuthorPostsResponse;
import com.sociame.app.core.usecases.posts.domain.responses.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GetOwnPostsService implements GetOwnPostsUseCase {

    private final GetCurrentAuthorUseCase authorUseCase;

    private final GetAuthorPostsUseCase postsUseCase;

    @Override
    public GetAuthorPostsResponse handleCommand(GetOwnPostsCommand command) {
        Author author = authorUseCase.getCurrentAuthor(command.username()).toDomain();

        List<PostResponse> posts = postsUseCase.getAuthorPosts(AuthorResponse.map(author));

        if (posts.isEmpty()) return new GetAuthorPostsResponse(new ArrayList<>());

        return new GetAuthorPostsResponse(posts);
    }

}
