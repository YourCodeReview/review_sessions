package com.sviatosss.like_booster.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JwtResponse {
    private Long id;
    private String token;
    private String email;
    private List<String> roles;

    public JwtResponse(Long id, String token, String email, List<String> roles) {
        this.id = id;
        this.token = token;
        this.email = email;
        this.roles = roles;
    }
}