package com.sociame.app.config.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Optional;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ErrorController extends ResponseEntityExceptionHandler {

    private final Clock clock;

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception exception,
            @Nullable Object body,
            HttpHeaders headers,
            HttpStatusCode statusCode,
            WebRequest request
    ) {
        ProblemDetail response = getStandardResponse(
                exception,
                request,
                statusCode,
                exception.getMessage(),
                null
        );

        return super.handleExceptionInternal(exception, response, headers, statusCode, request);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<ProblemDetail> handleConstraintViolation(
            ConstraintViolationException exception,
            WebRequest request,
            Authentication authentication
    ) {
        HashMap<Path, String> constraintPathDescriptionPairs = new LinkedHashMap<>();

        exception.getConstraintViolations().forEach(cv -> {
            constraintPathDescriptionPairs.computeIfPresent(
                    cv.getPropertyPath(),
                    (key, oldValue) -> oldValue + ", " + cv.getMessage()
            );
            constraintPathDescriptionPairs.putIfAbsent(cv.getPropertyPath(), cv.getMessage());
        });

        String message = "Constraint Violations";

        ProblemDetail response = getStandardResponse(
                exception,
                request,
                HttpStatus.BAD_REQUEST,
                message,
                constraintPathDescriptionPairs
        );

        log.info(
                "ErrorController - Known Exception - CONSTRAINT VIOLATION EXCEPTION\n\tUser: {}\n\tRequest: {}\n\tResponse: {}",
                userIdFromAuthentication(authentication),
                request,
                response
        );

        if (log.isDebugEnabled()) {
            log.debug("ErrorController - Known Exception - CONSTRAINT VIOLATION EXCEPTION - Stacktrace", exception);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    protected ResponseEntity<ProblemDetail> handleRejectedExecutionException(
            RuntimeException exception,
            WebRequest request,
            Authentication authentication
    ) {

        ProblemDetail response = getStandardResponse(
                exception,
                request,
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(),
                null
        );

        log.info(
                "ErrorController - Known Exception\n\tUser: {}\n\tRequest: {}\n\tResponse: {}",
                userIdFromAuthentication(authentication),
                request,
                response,
                exception
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
    private ProblemDetail getStandardResponse(
            Exception exception,
            WebRequest request,
            HttpStatusCode statusCode,
            String message,
            Serializable details
    ) {
        final ProblemDetail problemDetail = ProblemDetail.forStatus(statusCode);

        problemDetail.setTitle(exception.getClass().getSimpleName());
        problemDetail.setDetail(message);
        problemDetail.setProperty(
                "timestamp",
                Timestamp.valueOf(LocalDateTime.now(clock)).getTime()
        );

        if (details != null) {
            problemDetail.setProperty("details", details);
        }

        if (request instanceof ServletWebRequest) {
            HttpServletRequest httpRequest = ((ServletWebRequest) request).getRequest();
            problemDetail.setProperty(
                    "full-path",
                    httpRequest.getRequestURL()
                            + Optional.ofNullable(httpRequest.getQueryString())
                            .map(qs -> "?" + qs)
                            .orElse(""));
        }

        return problemDetail;
    }

    private String userIdFromAuthentication(Authentication authentication) {
        return (authentication != null) ? authentication.getName() : "";
    }

}
