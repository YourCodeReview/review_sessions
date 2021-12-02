package com.sviatosss.like_booster.service;



import com.sviatosss.like_booster.entity.User;
import com.sviatosss.like_booster.error.NotFoundException;
import com.sviatosss.like_booster.repository.UserRepository;
import com.sviatosss.like_booster.security.services.UserDetailsImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Log4j2
@Service
@Transactional
public class UserService {
//     implements IUserService

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private VerificationTokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder encoder;

//    @Autowired
//    private MailService mailService;

    public User get(long id) {
        log.info("find user by Id");
        return  userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User - " + id + " not found"));
    }

    public User getUserByEmail(String email){
        log.info("find user by Email");
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new NotFoundException("User with email - " + email + " not found"));
    }

    public User getCurrentUserFromDB(){
        log.info("find user by Email from SecurityContextHolder");
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return getUserByEmail(userDetails.getUsername());
    }

    public User getCurrentUserFromSecurity(){
        log.info("find user from SecurityContextHolder");
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new User(userDetails);
    }


//    public User updateUser(UserUpdateRequest userUpdateRequest){
//        User current = getCurrentUserFromDB();
//        if (userUpdateRequest.getEmail() != null)
//            current.setEmail(userUpdateRequest.getEmail());
//        if (userUpdateRequest.getAvatar()  != null)
//            current.setAvatar(userUpdateRequest.getAvatar());
//        return userRepository.save(current);
//    }



//    @Override
//    public boolean verificationToken(Long id, String VerificationToken) {
//        Long verificationTokenId = tokenRepository.deleteByTokenAndAndUser(VerificationToken, new User(id));
//        if (verificationTokenId != 0){
//            userRepository.updateActive(id);
//            System.out.println(verificationTokenId);
//            return true;
//        }else return false;
//    }

    //    @Override
//    public void createVerificationToken(User user, String token) {
//        VerificationToken myToken = new VerificationToken(token, user);
//        tokenRepository.save(myToken);
//    }
//
//    public int updatePasswordByEmail(String email){
//        Random random = new Random();
//        String password = String.valueOf(100000 + random.nextInt(900000));
//        System.out.println(password);
//        mailService.sendingPasswordByEmail(email, password);
//        return userRepository.updatePasswordByEmail(encoder.encode(password), email);
//    }

}