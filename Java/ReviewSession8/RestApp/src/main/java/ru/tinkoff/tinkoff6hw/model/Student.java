package ru.tinkoff.tinkoff6hw.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tinkoff.tinkoff6hw.validation.StudentConstraint;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@StudentConstraint
public class Student {
    private UUID id;
    private String name;
    private int age;
    private int grade;
}
