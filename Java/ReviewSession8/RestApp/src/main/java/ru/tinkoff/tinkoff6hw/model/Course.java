package ru.tinkoff.tinkoff6hw.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tinkoff.tinkoff6hw.validation.CourseConstraint;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@CourseConstraint
public class Course {
    private UUID id;
    private String name;
    private String description;
    private int requiredGrade;
}
