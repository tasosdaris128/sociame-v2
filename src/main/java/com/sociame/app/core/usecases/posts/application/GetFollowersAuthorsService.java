package com.sociame.app.core.usecases.posts.application;

import com.sociame.app.core.usecases.posts.application.ports.in.GetFollowersAuthorsUseCase;
import com.sociame.app.core.usecases.posts.application.ports.out.GetFollowersAuthorsPort;
import com.sociame.app.core.usecases.posts.domain.responses.AuthorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GetFollowersAuthorsService implements GetFollowersAuthorsUseCase {

    private final GetFollowersAuthorsPort port;

    @Override
    public List<AuthorResponse> getFollowers(long authorId) {
        return port.getFollowers(authorId);
    }

}
