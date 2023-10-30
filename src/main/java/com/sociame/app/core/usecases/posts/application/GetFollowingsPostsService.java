package com.sociame.app.core.usecases.posts.application;

import com.sociame.app.core.usecases.posts.application.ports.in.GetAuthorPostsUseCase;
import com.sociame.app.core.usecases.posts.application.ports.in.GetCurrentAuthorUseCase;
import com.sociame.app.core.usecases.posts.application.ports.in.GetFollowingPostsUseCase;
import com.sociame.app.core.usecases.posts.application.ports.in.GetFollowingsAuthorsUseCase;
import com.sociame.app.core.usecases.posts.domain.Author;
import com.sociame.app.core.usecases.posts.domain.commands.GetFollowingsPostsCommand;
import com.sociame.app.core.usecases.posts.domain.responses.AuthorResponse;
import com.sociame.app.core.usecases.posts.domain.responses.GetFollowingsPostsResponse;
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
public class GetFollowingsPostsService implements GetFollowingPostsUseCase {

    private final GetAuthorPostsUseCase postsUseCase;

    private final GetCurrentAuthorUseCase authorUseCase;

    private final GetFollowingsAuthorsUseCase followingsUseCase;

    @Override
    public GetFollowingsPostsResponse handleCommand(GetFollowingsPostsCommand command) {
        List<PostResponse> posts = new ArrayList<>();

        Author author = authorUseCase.getCurrentAuthor(command.username()).toDomain();

        List<AuthorResponse> followings = followingsUseCase.getFollowings(author.id());

        if (followings.isEmpty()) return new GetFollowingsPostsResponse(new ArrayList<>());

        followings.forEach(e -> {
            List<PostResponse> authorPosts = postsUseCase.getAuthorPosts(e);
            if (!authorPosts.isEmpty()) posts.addAll(authorPosts);
        });

        return new GetFollowingsPostsResponse(posts);
    }
}
