package ru.tinkoff.tinkoff6hw.validation;

import ru.tinkoff.tinkoff6hw.model.Course;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static java.util.Objects.isNull;

public class CourseValidator implements ConstraintValidator<CourseConstraint, Course> {

    @Override
    public boolean isValid(Course course, ConstraintValidatorContext constraintValidatorContext) {
        return !isNull(course.getName()) && (!course.getName().isBlank()
                || isNull(course.getDescription()) && (!course.getDescription().isBlank()
                || course.getRequiredGrade() != 0));
    }
}
