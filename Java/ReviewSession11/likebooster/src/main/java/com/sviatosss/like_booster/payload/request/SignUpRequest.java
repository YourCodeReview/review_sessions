package com.sviatosss.like_booster.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
public class SignUpRequest {
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    public SignUpRequest(@Email String email, @NotBlank @Size(min = 6, max = 40) String password) {
        this.email = email;
        this.password = password;
    }
}
