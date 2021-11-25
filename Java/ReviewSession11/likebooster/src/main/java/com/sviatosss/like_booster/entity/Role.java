package com.sviatosss.like_booster.entity;

import com.sviatosss.like_booster.entity.enums.ERole;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Size(min = 4, max = 20, message = "Please use 4 to 20 characters")
    private ERole name;

    public Role() {

    }
}