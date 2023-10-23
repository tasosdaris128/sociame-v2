package com.sociame.app.config.web.security;

import jakarta.validation.constraints.AssertTrue;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Data
// @ConfigurationProperties("management.endpoints.web.security")
@Component
@Validated
public class ActuatorSecurityProperties {

    @Value("${management.endpoints.web.security.enabled}")
    private boolean enabled = false;

    @Value("${management.endpoints.web.security.username}")
    private String username;

    @Value("${management.endpoints.web.security.password}")
    private String password;

    @AssertTrue
    public boolean isValid() {
        if (!enabled) {
            return true;
        }
        return isNotBlank(username) && isNotBlank(password);
    }

    private boolean isNotBlank(String value) {
        return value != null && !value.isBlank();
    }
}
