package com.sociame.app.config.web.security;

import com.sociame.app.core.usecases.users.application.ports.in.UserDetailsFromJWTokenUseCase;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Getter
@Component
public class SecurityConfigurationProperties {
    private final PasswordEncoder passwordEncoder;
    private final ActuatorSecurityProperties actuatorSecurityProperties;
    private final UserDetailsFromJWTokenUseCase userDetailsFromJWTokenUseCase;

    public SecurityConfigurationProperties(
            PasswordEncoder encoder,
            ActuatorSecurityProperties properties,
            UserDetailsFromJWTokenUseCase userCase
    ) {
        this.passwordEncoder = encoder;
        this.actuatorSecurityProperties = properties;
        this.userDetailsFromJWTokenUseCase = userCase;
    }
}
