package ru.tinkoff.tinkoff6hw.validation;

import org.springframework.beans.factory.annotation.Autowired;
import ru.tinkoff.tinkoff6hw.model.StudentCourses;
import ru.tinkoff.tinkoff6hw.service.CourseService;
import ru.tinkoff.tinkoff6hw.service.StudentService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.util.NoSuchElementException;
import java.util.UUID;

import static java.lang.String.format;
import static ru.tinkoff.tinkoff6hw.exception.ApplicationError.ENTITY_NOT_FOUND;

public class StudentGradeValidator implements ConstraintValidator<StudentConstraint, StudentCourses> {

    @Autowired
    StudentService studentService;

    @Autowired
    CourseService courseService;

    @Override
    public boolean isValid(StudentCourses mapping, ConstraintValidatorContext constraintValidatorContext) {

        try {
            studentService.findById(mapping.getId());
        } catch (NoSuchElementException e) {
            throw ENTITY_NOT_FOUND.exception(format("student with id {%s} doesn't exist", mapping.getId()));
        }

        UUID wrongId = null;
        try {
            for (UUID id : mapping.getCoursesId()) {
                wrongId = id;
                courseService.findById(id);
            }
        } catch (NoSuchElementException e) {
            throw ENTITY_NOT_FOUND.exception(format("course with id {%s} doesn't exist", wrongId));
        }

        return mapping.getCoursesId().stream()
                .allMatch(a -> courseService.findById(a)
                        .getRequiredGrade()
                        <= studentService.findById(mapping.getId()).getGrade());
    }
}
