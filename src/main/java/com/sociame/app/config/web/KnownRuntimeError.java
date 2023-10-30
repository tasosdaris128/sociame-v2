package com.sociame.app.config.web;

public class KnownRuntimeError extends RuntimeException {

    public enum ErrorType {
        BAD_REQUEST,
        NOT_FOUND
    }

    private final ErrorType errorType;

    public KnownRuntimeError(String message) {
        super(message);
        this.errorType = ErrorType.BAD_REQUEST;
    }

    public KnownRuntimeError(String message, Throwable throwable) {
        super(message, throwable);
        this.errorType = ErrorType.BAD_REQUEST;
    }

    public KnownRuntimeError(String message, Throwable throwable, ErrorType errorType) {
        super(message, throwable);
        this.errorType = errorType;
    }

    public ErrorType errorType() {
        return this.errorType;
    }

}
