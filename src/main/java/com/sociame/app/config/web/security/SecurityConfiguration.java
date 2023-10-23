package com.sociame.app.config.web.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Slf4j
public class SecurityConfiguration {

    private final SecurityConfigurationProperties props;

    public SecurityConfiguration(SecurityConfigurationProperties optionsPlaceholder) {
        this.props = optionsPlaceholder;
    }

    private FilterForJWTInHeader oncePerRequestTenant() {
        return new FilterForJWTInHeader(new JWTAuthenticationProvider(props.getUserDetailsFromJWTokenUseCase()));
    }

    @Bean
    @Order(1)
    public SecurityFilterChain filterChainTenant(HttpSecurity http) throws Exception {
        return http.securityMatcher("/api/**")
                .addFilterBefore(oncePerRequestTenant(), AnonymousAuthenticationFilter.class)
                .cors(Customizer.withDefaults())
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .requestCache(RequestCacheConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
                    authorizationManagerRequestMatcherRegistry
                            .requestMatchers("/api/**")
                            .authenticated();
                })
                .build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain filterChainForUnprotected(HttpSecurity http) throws Exception {
        return http.securityMatcher("/rest/**", "/token/**")
                .cors(Customizer.withDefaults())
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .requestCache(RequestCacheConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
                    authorizationManagerRequestMatcherRegistry
                            .requestMatchers("/rest/**", "/token/**")
                            .permitAll();
                })
                .build();
    }

    @ConditionalOnProperty(
            name = "management.endpoints.web.security.enabled",
            havingValue = "true",
            matchIfMissing = false)
    @Bean
    @Order(3)
    public SecurityFilterChain filterChainActuator(HttpSecurity http) throws Exception {

        return http.securityMatcher("/actuator/**")
                .cors(Customizer.withDefaults())
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .requestCache(RequestCacheConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .httpBasic(basicConfig -> basicConfig.realmName("actuator realm"))
                .authenticationProvider(new ActuatorAuthenticationProvider(new InMemoryUserDetailsManager(User.builder()
                        .username(props.getActuatorSecurityProperties().getUsername())
                        .password(props.getActuatorSecurityProperties().getPassword())
                        .build())))
                .authorizeHttpRequests(
                        authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
                                .requestMatchers("/actuator/**")
                                .authenticated())
                .build();
    }

    @ConditionalOnProperty(
            name = "management.endpoints.web.security.enabled",
            havingValue = "false",
            matchIfMissing = true)
    @Bean
    @Order(3)
    public SecurityFilterChain filterChainActuatorNoAuth(HttpSecurity http) throws Exception {
        return http.securityMatcher("/actuator/**")
                .cors(Customizer.withDefaults())
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .requestCache(RequestCacheConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
                                .requestMatchers("/actuator/**")
                                .permitAll())
                .build();
    }

    @Bean
    @Order(4)
    public SecurityFilterChain filterChainForRemainingEndpoints(HttpSecurity http) throws Exception {
        return http.sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(Customizer.withDefaults())
                .csrf(Customizer.withDefaults())
                .requestCache(Customizer.withDefaults())
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .build();
    }

}
