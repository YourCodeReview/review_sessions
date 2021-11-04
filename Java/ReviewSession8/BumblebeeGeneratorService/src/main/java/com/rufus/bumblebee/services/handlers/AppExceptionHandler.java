package com.rufus.bumblebee.services.handlers;

import com.rufus.bumblebee.controllers.responses.BaseErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<BaseErrorResponse> handle(Exception ex) {
        HttpStatus status;
        BaseErrorResponse response;

        if (ex instanceof MethodArgumentNotValidException) {
            status = HttpStatus.BAD_REQUEST;
            response = handleRqValidateException((MethodArgumentNotValidException) ex);

        } else if (ex instanceof ValidationException) {
            status = HttpStatus.BAD_REQUEST;
            response = handleValidationException((ValidationException) ex);
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            response = handleBaseException(ex);
        }
        return new ResponseEntity<>(response, status);
    }

    private BaseErrorResponse handleRqValidateException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new BaseErrorResponse<Map<String, String>>().setErrorMessage(errors);
    }

    private BaseErrorResponse handleBaseException(Exception ex) {
        BaseErrorResponse response = new BaseErrorResponse<String>();
        response.setErrorMessage(ex.getMessage());
        return response;
    }

    private BaseErrorResponse handleValidationException(ValidationException exception) {
        if (exception.getErrors() == null) {
            return new BaseErrorResponse<String>().setErrorMessage(exception.getMessage());
        }
        Map<String, String> errors = new HashMap<>();
        exception.getErrors().stream().forEach(error -> {
            errors.put(error.getObjectName(), error.getDefaultMessage());
        });
        return new BaseErrorResponse<Map<String, String>>().setErrorMessage(errors);
    }
}
