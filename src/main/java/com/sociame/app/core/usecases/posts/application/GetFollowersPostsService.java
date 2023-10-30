package com.sociame.app.core.usecases.posts.application;

import com.sociame.app.core.usecases.posts.application.ports.in.GetAuthorPostsUseCase;
import com.sociame.app.core.usecases.posts.application.ports.in.GetCurrentAuthorUseCase;
import com.sociame.app.core.usecases.posts.application.ports.in.GetFollowersAuthorsUseCase;
import com.sociame.app.core.usecases.posts.application.ports.in.GetFollowersPostsUseCase;
import com.sociame.app.core.usecases.posts.domain.Author;
import com.sociame.app.core.usecases.posts.domain.commands.GetFollowersPostsCommand;
import com.sociame.app.core.usecases.posts.domain.responses.AuthorResponse;
import com.sociame.app.core.usecases.posts.domain.responses.GetFollowersPostsResponse;
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
public class GetFollowersPostsService implements GetFollowersPostsUseCase {

    private final GetCurrentAuthorUseCase authorUseCase;

    private final GetAuthorPostsUseCase postsUseCase;

    private final GetFollowersAuthorsUseCase followersUseCase;

    @Override
    public GetFollowersPostsResponse handleCommand(GetFollowersPostsCommand command) {
        List<PostResponse> posts = new ArrayList<>();

        Author author = authorUseCase.getCurrentAuthor(command.username()).toDomain();

        List<AuthorResponse> followers = followersUseCase.getFollowers(author.id());

        if (followers.isEmpty()) return new GetFollowersPostsResponse(new ArrayList<>());

        followers.forEach(e -> {
            List<PostResponse> authorPosts = postsUseCase.getAuthorPosts(e);
            if (!authorPosts.isEmpty()) posts.addAll(authorPosts);
        });

        return new GetFollowersPostsResponse(posts);
    }

}
