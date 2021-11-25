package com.sviatosss.like_booster.controller;

import com.sviatosss.like_booster.entity.User;
import com.sviatosss.like_booster.error.AlreadyExistsException;
import com.sviatosss.like_booster.payload.request.UserRequest;
import com.sviatosss.like_booster.payload.response.ValidResponse;
import com.sviatosss.like_booster.repository.UserRepository;
import com.sviatosss.like_booster.service.UserAuthService;
import com.sviatosss.like_booster.service.UserService;
import com.sviatosss.like_booster.utils.EmailValidator;
import com.sviatosss.like_booster.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/sign-in")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody UserRequest userRequest, BindingResult bindingResult) throws EntityNotFoundException {
        if (bindingResult.hasErrors()){
            return new Validator().getErrors(bindingResult);
        }
        return new ResponseEntity<>(userAuthService.signIn(userRequest), HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRequest userRequest, BindingResult bindingResult) throws AlreadyExistsException {
//        if (bindingResult.hasErrors()){
//            return new Validator().getErrors(bindingResult);
//        }
        return new ResponseEntity<>(userAuthService.signUp(userRequest), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Transactional
    @PostMapping("/add-manager")
    public ResponseEntity<?> registerManagerByAdmin(@Valid @RequestBody UserRequest userRequest, BindingResult bindingResult)  throws EntityNotFoundException  {
        if (bindingResult.hasErrors()){
            return new Validator().getErrors(bindingResult);
        }
        return new ResponseEntity<>(userAuthService.addManager(userRequest), HttpStatus.OK);
    }


    @GetMapping("/check/email/{email}")
    public ResponseEntity<?> checkEmail(@PathVariable String email) {
        ResponseEntity<?> responseEntity = EmailValidator.validate(email);
        if (responseEntity != null){
            return responseEntity;
        }
        boolean valid = userAuthService.validEmail(email);
        return new ResponseEntity<>(new ValidResponse(valid), valid ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

//    @PreAuthorize("hasAnyRole('ROLE_STUDENT', 'ROLE_TEACHER', 'ROLE_CONTENT_MANAGER', 'ROLE_ADMIN')")
//    @PostMapping("/update-jwt")
//    public ResponseEntity<?> updateJwt() {
//        Map<String, String> token = new HashMap<>();
//        token.put("token", userAuthService.updateJWT());
//        return new ResponseEntity<>(token, HttpStatus.OK);
//    }

    @GetMapping("/current")
    public User getCurrentUser(){
        return userService.getCurrentUserFromDB();
    }
}