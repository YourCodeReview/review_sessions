package ru.tinkoff.tinkoff6hw.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;

public enum ApplicationError {

    ENTITY_NOT_FOUND("Entity not found", 404);

    private final String message;
    private final int code;

    ApplicationError(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public ApplicationException exception(String args) {
        return new ApplicationException(this, args);
    }

    public static class ApplicationException extends RuntimeException {

        public final ApplicationExceptionCompanion companion;

        ApplicationException(ApplicationError error, String message) {
            super(error.message + ": " + message);
            this.companion = new ApplicationExceptionCompanion(error.code, error.message + ": " + message);
        }

        public record ApplicationExceptionCompanion(@JsonIgnore int code, String message){}
    }
}
