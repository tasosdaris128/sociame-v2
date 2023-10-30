package com.sociame.app.core.usecases.posts.application;

import com.sociame.app.core.usecases.posts.application.ports.in.GetPostUseCase;
import com.sociame.app.core.usecases.posts.application.ports.out.GetPostPort;
import com.sociame.app.core.usecases.posts.domain.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GetPostService implements GetPostUseCase {

    private final GetPostPort port;

    @Override
    public Optional<Post> getPost(long postId) {
        return port.getPost(postId);
    }

}
