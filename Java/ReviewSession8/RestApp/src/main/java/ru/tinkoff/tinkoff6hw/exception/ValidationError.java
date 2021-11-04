package ru.tinkoff.tinkoff6hw.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;

public enum ValidationError {

    ENTITY_IS_NULL("Entity is null", 400),
    STUDENTS_GRADE_IS_SMALL("Student's grade is unsatisfactory to that course(s)", 400);

    private final String message;
    private final int code;

    ValidationError(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public ValidationException exception() {
        return new ValidationException(this);
    }

    public static class ValidationException extends RuntimeException {

        //TODO: understand why companion is used
        public final ValidationExceptionCompanion companion;

        ValidationException(ValidationError error) {
            super(error.message);
            this.companion = new ValidationExceptionCompanion(error.code, error.message);
        }

        public record ValidationExceptionCompanion(@JsonIgnore int code, String message){}
    }
}
