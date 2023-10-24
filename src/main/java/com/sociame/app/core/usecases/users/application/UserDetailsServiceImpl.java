package com.sociame.app.core.usecases.users.application;

import com.sociame.app.core.usecases.users.application.ports.out.UserDetailsServicePort;
import com.sociame.app.core.usecases.utils.annotations.ReadTransactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDetailsServicePort userDetailsServicePort;

    @ReadTransactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Load user by username: {}", username);
        return userDetailsServicePort
                .loadUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Could not find user with username: " + username));
    }

}
