package com.sviatosss.like_booster.service.impl;

import com.sviatosss.like_booster.entity.User;

public interface IUserService {

    void createVerificationToken(User user, String token);
    boolean verificationToken(Long id, String VerificationToken);
}