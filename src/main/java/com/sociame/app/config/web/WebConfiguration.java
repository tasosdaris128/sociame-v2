package com.sociame.app.config.web;

import com.sociame.app.config.web.cors.CorsProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfiguration {

    @Bean
    public WebConfigurator appWebConfigurator(CorsProperties properties) {
        return new WebConfigurator(properties);
    }

}
