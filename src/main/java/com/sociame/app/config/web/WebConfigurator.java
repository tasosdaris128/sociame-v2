package com.sociame.app.config.web;

import com.sociame.app.config.web.cors.CorsProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
public class WebConfigurator implements WebMvcConfigurer {

    private final CorsProperties corsProperties;

    public WebConfigurator(CorsProperties properties) {
        this.corsProperties = properties;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOriginPatterns(corsProperties.getPattern().orElse("*"))
                .allowedMethods(
                        HttpMethod.GET.name(),
                        HttpMethod.HEAD.name(), // @Cleanup: Maybe not?
                        HttpMethod.POST.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.DELETE.name()
                );
    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configuration) {
        configuration.setDefaultTimeout(1_000 * 60);
    }

}
