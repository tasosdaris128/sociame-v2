package com.sociame.app.config.web.security;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Data
@Component
@Validated
public class JWTSecurityProperties {
    @NotBlank
    @Value("${options.jwt-settings.issuer}")
    private String issuer;

    @NotNull
    @Min(1)
    @Value("${options.jwt-settings.expirationInMinutes}")
    private Integer expirationInMinutes;

    @NotBlank
    @Value("${options.jwt-settings.secret}")
    private String secret;

    @NotBlank
    @Value("${options.db.secret}")
    private String salt;
}
