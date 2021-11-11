package ru.tinkoff.tinkoff6hw.validation;

import ru.tinkoff.tinkoff6hw.model.Student;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static java.util.Objects.isNull;

public class StudentNullValidator implements ConstraintValidator<StudentConstraint, Student> {

    @Override
    public boolean isValid(Student student, ConstraintValidatorContext constraintValidatorContext) {
        return !isNull(student.getName()) && (!student.getName().isBlank()
                || student.getAge() != 0
                || student.getGrade() != 0);
    }
}
