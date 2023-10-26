package com.sociame.app.core.usecases.posts.application;

import com.sociame.app.core.usecases.posts.application.ports.in.GetFollowingPostsUseCase;
import com.sociame.app.core.usecases.posts.application.ports.out.GetAuthorPostsPort;
import com.sociame.app.core.usecases.posts.application.ports.out.GetCurrentAuthorPort;
import com.sociame.app.core.usecases.posts.application.ports.out.GetFollowingsAuthorsPort;
import com.sociame.app.core.usecases.posts.domain.Author;
import com.sociame.app.core.usecases.posts.domain.GetFollowingsPostsCommand;
import com.sociame.app.core.usecases.posts.domain.Post;
import com.sociame.app.core.usecases.posts.domain.responses.GetFollowingsPostsResponse;
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
public class GetFollowingsPostsService implements GetFollowingPostsUseCase {

    private final GetAuthorPostsPort postPort;

    private final GetCurrentAuthorPort authorPort;

    private final GetFollowingsAuthorsPort followingsPort;

    @Override
    public Optional<GetFollowingsPostsResponse> handleCommand(GetFollowingsPostsCommand command) {
        List<Post> posts = new ArrayList<>();

        Optional<Author> optionalAuthor = authorPort.getCurrentAuthor(command.username());

        if (optionalAuthor.isEmpty()) return Optional.of(new GetFollowingsPostsResponse(new ArrayList<>()));

        Author currentAuthor = optionalAuthor.get();

        List<Author> followings = followingsPort.getFollowings(currentAuthor.id());

        if (followings.isEmpty()) return Optional.of(new GetFollowingsPostsResponse(new ArrayList<>()));

        followings.forEach(e -> {
            List<Post> authorPosts = postPort.getAuthorPosts(e);
            if (!authorPosts.isEmpty()) posts.addAll(authorPosts);
        });

        return Optional.of(new GetFollowingsPostsResponse(posts.stream().map(PostResponse::map).toList()));
    }
}
