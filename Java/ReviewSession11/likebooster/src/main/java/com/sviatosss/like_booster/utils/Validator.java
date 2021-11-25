package com.sviatosss.like_booster.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.io.IOException;
import java.util.List;

public class Validator {

    public ResponseEntity<?> getErrors(BindingResult bindingResult) {
        System.out.println(bindingResult.toString());
        StringBuilder messages = new StringBuilder("{\n");
        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors ) {
            messages.append("\"").append(error.getField()).append("\" : \"")
                    .append(error.getDefaultMessage()).append("\",\n");
        }
        messages.deleteCharAt(messages.length()-2).append("}");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonObject = null;
        try {
            jsonObject = mapper.readTree(String.valueOf(messages));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(jsonObject, HttpStatus.BAD_REQUEST);
    }
}
