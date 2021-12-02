package com.sviatosss.like_booster.service;


import com.sviatosss.like_booster.entity.Role;
import com.sviatosss.like_booster.entity.User;
import com.sviatosss.like_booster.entity.enums.ERole;
import com.sviatosss.like_booster.error.AlreadyExistsException;
import com.sviatosss.like_booster.error.EntityNotFoundException;
import com.sviatosss.like_booster.error.MessageResponse;
import com.sviatosss.like_booster.payload.request.UserRequest;
import com.sviatosss.like_booster.payload.response.JwtResponse;
import com.sviatosss.like_booster.repository.RoleRepository;
import com.sviatosss.like_booster.repository.UserRepository;
import com.sviatosss.like_booster.security.jwt.JwtUtils;
import com.sviatosss.like_booster.security.services.UserDetailsImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@Service
public class UserAuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    public JwtResponse signIn(UserRequest userRequest){
        Optional<User> optionalUser = userRepository.findUserByEmail(userRequest.getEmail());
        if (!optionalUser.isPresent())
            throw new EntityNotFoundException(User.class, "email", userRequest.getEmail());

        return authentication(userRequest);
    }

    public JwtResponse authentication(UserRequest userRequest){
        log.info("authenticate email - " + userRequest.getEmail() + "; password  - " + userRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userRequest.getEmail(), userRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles_list = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new JwtResponse(userDetails.getId(), jwt, userRequest.getEmail(), roles_list);
    }


    @Transactional
    public JwtResponse signUp(UserRequest userRequest){

        if (userRepository.existsByEmail(userRequest.getEmail()))
            throw new AlreadyExistsException(User.class, "email", userRequest.getEmail());

        User user = new User( userRequest.getEmail(),
                    encoder.encode(userRequest.getPassword()));

        Set<Role> roles = new HashSet<>();

        log.info("set role - ROLE_FREELANCER and ROLE_CUSTOMER");
        Role roleEmployee = roleRepository.findByName(ERole.ROLE_FREELANCER)
                .orElseThrow(() -> new RuntimeException("Error: ROLE_FREELANCER is not found."));
        Role roleCustomer = roleRepository.findByName(ERole.ROLE_CUSTOMER)
                .orElseThrow(() -> new RuntimeException("Error: ROLE_CUSTOMER is not found."));

        roles.add(roleEmployee);
        roles.add(roleCustomer);

        user.setRoles(roles);

        log.info("save user to DB");
        userRepository.save(user);

        return authentication(userRequest);
    }

    public User addManager(UserRequest userRequest){
        if (userRepository.existsByEmail(userRequest.getEmail()))
            throw new EntityNotFoundException(User.class, "email", userRequest.getEmail());

        User user = new User( userRequest.getEmail(),
                encoder.encode(userRequest.getPassword()));

        Set<Role> roles = new HashSet<>();

        log.info("set role - ROLE_CONTENT_MANAGER");
        Role roleAdmin = roleRepository.findByName(ERole.ROLE_CONTENT_MANAGER)
                .orElseThrow(() -> new RuntimeException("Error: ROLE_CONTENT_MANAGER is not found."));

        roles.add(roleAdmin);
        user.setRoles(roles);

        return userRepository.save(user);
    }

    public boolean validEmail(String email){
        log.info("check if the email is exists ? ");
        return !userRepository.existsByEmail(email);
    }

    public String updateJWT(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtUtils.generateJwtToken(authentication);
    }
}
