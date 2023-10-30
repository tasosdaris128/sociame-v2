package com.sociame.app.core.usecases.posts.application;

import com.sociame.app.core.usecases.posts.application.ports.in.GetCurrentAuthorUseCase;
import com.sociame.app.core.usecases.posts.application.ports.out.GetCurrentAuthorPort;
import com.sociame.app.core.usecases.posts.domain.Author;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GetCurrentAuthorService implements GetCurrentAuthorUseCase {

    private final GetCurrentAuthorPort port;

    @Override
    public Optional<Author> getCurrentAuthor(String username) {
        return port.getCurrentAuthor(username);
    }

}
