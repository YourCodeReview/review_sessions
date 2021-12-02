package com.sviatosss.like_booster.utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class EmailValidator {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean isValid(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    public static ResponseEntity<?> validate(String email) {
        if (!isValid(email)){

            Map<String, Boolean> hm = new HashMap<>();
            hm.put("valid", false);

            return new ResponseEntity<>(hm, HttpStatus.BAD_REQUEST);
        }else return null;
    }
}