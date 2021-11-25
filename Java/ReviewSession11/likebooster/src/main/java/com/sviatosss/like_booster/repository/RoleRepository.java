package com.sviatosss.like_booster.repository;

import com.sviatosss.like_booster.entity.Role;
import com.sviatosss.like_booster.entity.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}