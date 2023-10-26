package com.sociame.app.core.usecases.posts.application;

import com.sociame.app.core.usecases.posts.application.ports.in.GetFollowersPostsUseCase;
import com.sociame.app.core.usecases.posts.application.ports.out.GetAuthorPostsPort;
import com.sociame.app.core.usecases.posts.application.ports.out.GetCurrentAuthorPort;
import com.sociame.app.core.usecases.posts.application.ports.out.GetFollowersAuthorsPort;
import com.sociame.app.core.usecases.posts.domain.Author;
import com.sociame.app.core.usecases.posts.domain.GetFollowersPostsCommand;
import com.sociame.app.core.usecases.posts.domain.Post;
import com.sociame.app.core.usecases.posts.domain.responses.GetFollowersPostsResponse;
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
public class GetFollowersPostsService implements GetFollowersPostsUseCase {

    private final GetAuthorPostsPort postPort;

    private final GetCurrentAuthorPort authorPort;

    private final GetFollowersAuthorsPort followersPort;

    @Override
    public Optional<GetFollowersPostsResponse> handleCommand(GetFollowersPostsCommand command) {
        List<Post> posts = new ArrayList<>();

        Optional<Author> optionalAuthor = authorPort.getCurrentAuthor(command.username());

        if (optionalAuthor.isEmpty()) return Optional.of(new GetFollowersPostsResponse(new ArrayList<>()));

        Author currentAuthor = optionalAuthor.get();

        List<Author> followers = followersPort.getFollowers(currentAuthor.id());

        if (followers.isEmpty()) return Optional.of(new GetFollowersPostsResponse(new ArrayList<>()));

        followers.forEach(e -> {
            List<Post> authorPosts = postPort.getAuthorPosts(e);
            if (!authorPosts.isEmpty()) posts.addAll(authorPosts);
        });

        return Optional.of(new GetFollowersPostsResponse(posts.stream().map(PostResponse::map).toList()));
    }

}
