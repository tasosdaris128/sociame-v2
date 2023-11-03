package com.sociame.app.dbconfig;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(value = "it")
@SpringBootTest(
        classes = TestDatabaseConfiguration.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class ApplicationPortConfiguration {

    @LocalServerPort
    public int port;

}
