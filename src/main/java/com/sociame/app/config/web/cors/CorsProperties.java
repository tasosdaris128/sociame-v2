package com.sociame.app.config.web.cors;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Data
// @ConfigurationProperties("spring.cors") for some reason, this gives me compilation error.
@Component
public class CorsProperties {

    @Value("${cors.url}")
    private String url;

    @Value("${cors.pattern}")
    private String pattern;

    public Optional<String> getPattern() {
        return Optional.ofNullable(pattern);
    }

}
