package com.sociame.app.core.usecases.posts;

import com.fasterxml.jackson.databind.JsonNode;
import com.sociame.app.dbconfig.ApplicationPortConfiguration;
import com.sociame.app.utils.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Sql(
        scripts = {"prepare_posts.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@Sql(
        scripts = {"clear_post.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
)
@Slf4j
public class PostsIntegrationTests extends ApplicationPortConfiguration implements JsonMapper {

    RestTemplate restTemplate;

    @BeforeEach
    void setup() {
        if (restTemplate == null) {
            String rootUri = "http://localhost:" + port;
            restTemplate = new RestTemplateBuilder()
                    .rootUri(rootUri)
                    .build();

            log.info("Performing requests at: {}", rootUri);
        }
    }

    @DisplayName("Dummy test")
    @Test
    void justADummyTest() {
        assertThat(true).isTrue();
    }

    @DisplayName("Create sixth comment on post with free plan")
    @Test
    void createSixthCommentWithFreePlan() {
        final IT it = new IT();
        it.login("freeplan@example.com", "123456789");
        it.createCommentWithFreePlan(1L, "Free Comment 5");
    }

    @DisplayName("Create sixth comment on post with premium plan")
    @Test
    void createSixthCommentWithPremiumPlan() {
        final IT it = new IT();
        it.login("premiumplan@example.com", "123456789");
        it.createCommentWithPremiumPlan(1L, "Premium Comment 5");
    }

    private class IT {

        String token;

        void login(String username, String password) {

            log.info("Login with: {}, {}", username, password);

            final JsonNode response = restTemplate.postForObject(
                    "/token/login",
                    mapper.createObjectNode().put("username", username).put("password", password),
                    JsonNode.class
            );

            assertThat(response).isNotNull();
            assertThat(response.hasNonNull("token")).isTrue();
            assertThat(response.get("token").isTextual()).isTrue();

            this.token = response.get("token").textValue();

            log.info("Response token: {}", this.token);

        }

        void createCommentWithFreePlan(long postId, String comment) {

            log.info("Creating comment {} for post {} with free plan.", comment, postId);

            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));
            headers.setBearerAuth(this.token);

            final RestClientResponseException responseException = catchThrowableOfType(
                    () -> restTemplate.exchange(
                            "/api/auth/post/comment/" + postId,
                            HttpMethod.POST,
                            new HttpEntity<>(mapper.createObjectNode().put("comment", comment), headers),
                            JsonNode.class
                    ),
                    RestClientResponseException.class
            );

            assertThat(responseException.getStatusCode().value()).isEqualTo(400);

        }

        void createCommentWithPremiumPlan(long postId, String comment) {

            log.info("Creating comment {} for post {} with premium plan", comment, postId);

            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));
            headers.setBearerAuth(this.token);

            ResponseEntity<JsonNode> response = restTemplate.exchange(
                    "/api/auth/post/comment/" + postId,
                    HttpMethod.POST,
                    new HttpEntity<>(mapper.createObjectNode().put("comment", comment), headers),
                    JsonNode.class
            );

            assertThat(response.getStatusCode().value()).isEqualTo(200);

        }

    }

}
