package com.sociame.app.core.usecases.posts.application;

import com.sociame.app.core.usecases.posts.application.ports.in.GetFollowingsAuthorsUseCase;
import com.sociame.app.core.usecases.posts.application.ports.out.GetFollowingsAuthorsPort;
import com.sociame.app.core.usecases.posts.domain.Author;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GetFollowingsAuthorsService implements GetFollowingsAuthorsUseCase {

    private final GetFollowingsAuthorsPort port;

    @Override
    public List<Author> getFollowings(long authorId) {
        return port.getFollowings(authorId);
    }

}
