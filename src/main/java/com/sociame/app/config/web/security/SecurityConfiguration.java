package com.sociame.app.config.web.security;

import com.sociame.app.core.usecases.users.application.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Slf4j
public class SecurityConfiguration {

    private final UserDetailsServiceImpl userDetailsService;

    private final SecurityConfigurationProperties props;

    public SecurityConfiguration(UserDetailsServiceImpl userDetailsService, SecurityConfigurationProperties optionsPlaceholder) {
        this.userDetailsService = userDetailsService;
        this.props = optionsPlaceholder;
    }

    private FilterForJWTInHeader oncePerRequestTenant() {
        return new FilterForJWTInHeader(new JWTAuthenticationProvider(props.getUserDetailsFromJWTokenUseCase()));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("Setting authentication manager...");
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        AuthenticationManager manager = authenticationManagerBuilder.build();
        http.authenticationManager(manager);

        log.info("Set CORS...");
        http.cors(Customizer.withDefaults());

        log.info("Disable CSRF...");
        http.csrf(AbstractHttpConfigurer::disable);

        log.info("Set session management...");
        http.sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        log.info("Authorize patterns...");
        http.authorizeHttpRequests(registry -> {
            registry.requestMatchers("/api/**").authenticated();
            registry.requestMatchers("/token/**").permitAll();
        });

        log.info("Adding filter before...");
        http.addFilterBefore(oncePerRequestTenant(), AnonymousAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
