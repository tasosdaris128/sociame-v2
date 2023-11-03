package com.sociame.app.core.usecases.posts;

import com.sociame.app.dbconfig.ApplicationPortConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

public class PostsIntegrationTests extends ApplicationPortConfiguration {

    RestTemplate restTemplate;

    @BeforeEach
    void setup() {
        if (restTemplate == null) {
            restTemplate = new RestTemplateBuilder()
                    .rootUri("http://localhost:" + port)
                    .build();
        }
    }

    @DisplayName("Dummy test")
    @Test
    void justADummyTest() {
        assertTrue(true, "Just a dummy test.");
    }

}
