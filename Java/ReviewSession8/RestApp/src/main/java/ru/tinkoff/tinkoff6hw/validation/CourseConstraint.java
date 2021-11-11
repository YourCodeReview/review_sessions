package ru.tinkoff.tinkoff6hw.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CourseValidator.class)
public @interface CourseConstraint {

    String message() default "Course entity is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
