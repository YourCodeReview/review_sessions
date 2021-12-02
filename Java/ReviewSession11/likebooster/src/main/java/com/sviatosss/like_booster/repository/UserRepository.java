package com.sviatosss.like_booster.repository;

import com.sviatosss.like_booster.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsById(Long id);

    Boolean existsByEmail(String email);

    Optional<User> findUserById(Long id);

    Optional<User> findUserByEmail(String email);
}