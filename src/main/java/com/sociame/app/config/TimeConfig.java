package com.sociame.app.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

/**
 * Application time configuration. We will use plain UTC as out
 * application timezone (and let the frontend decide the repre-
 * santation of the time).
 *
 * @author Tasos Daris <tasos.daris@datawise.ai>
 */
@Configuration
public class TimeConfig {

    private static final ZoneId DEFAULT = ZoneId.of("UTC");

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

    @PostConstruct
    public void timezone() {
        TimeZone.setDefault(TimeZone.getTimeZone(DEFAULT));
    }

    @Bean
    Clock clock() {
        return Clock.system(DEFAULT);
    }

    @Bean
    ZoneId zoneId() {
        return DEFAULT;
    }

}
