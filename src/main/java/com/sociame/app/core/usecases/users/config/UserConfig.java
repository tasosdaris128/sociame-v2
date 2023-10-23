package com.sociame.app.core.usecases.users.config;

import com.sociame.app.config.web.security.JWTSecurityProperties;
import com.sociame.app.core.usecases.users.application.JWTLocalTokenService;
import com.sociame.app.core.usecases.users.domain.JWTSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
class UserConfig {
    @Bean
    JWTLocalTokenService service(JWTSecurityProperties properties, Clock clock) {
        JWTSettings settings =
                new JWTSettings(properties.getIssuer(), properties.getExpirationInMinutes(), properties.getSecret());
        return new JWTLocalTokenService(settings, clock);
    }
}
