package ru.tinkoff.tinkoff6hw.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.tinkoff6hw.exception.ApplicationError;
import ru.tinkoff.tinkoff6hw.exception.ValidationError;
import ru.tinkoff.tinkoff6hw.model.Student;
import ru.tinkoff.tinkoff6hw.model.StudentCourses;
import ru.tinkoff.tinkoff6hw.service.CourseService;
import ru.tinkoff.tinkoff6hw.service.StudentService;

import java.util.NoSuchElementException;
import java.util.UUID;

import static java.lang.String.format;
import static ru.tinkoff.tinkoff6hw.exception.ApplicationError.ENTITY_NOT_FOUND;
import static ru.tinkoff.tinkoff6hw.exception.ValidationError.ENTITY_IS_NULL;
import static ru.tinkoff.tinkoff6hw.exception.ValidationError.STUDENTS_GRADE_IS_SMALL;

@RestController
public class StudentController {

    private static final Logger log = LoggerFactory.getLogger(StudentController.class);

    private final StudentService studentService;

    public StudentController(StudentService service) {
        this.studentService = service;
    }

    //TODO: give exception when putting same student
    @PostMapping(value = "api/student/save", consumes = "application/json")
    public void saveStudent(@RequestBody @Validated Student student, BindingResult errors) {
        if(errors.hasErrors()) throw ENTITY_IS_NULL.exception();

        studentService.save(student);
        log.info("New Student with the name {} has been added", student.getName());
    }

    @PutMapping(value = "api/student/update", consumes = "application/json")
    public void updateStudent(@RequestBody @Validated Student student, BindingResult errors) {
        if(errors.hasErrors()) throw ENTITY_IS_NULL.exception();
        checkIsEntityAbsent(student.getId());

        studentService.update(student);
        log.info("Student with the name {} has been updated", student.getName());
    }

    //many browsers ignore the entity body with delete method, so it's done via id
    @DeleteMapping(value = "api/student/delete/{id}")
    public void deleteStudent(@PathVariable("id") UUID id) {
        checkIsEntityAbsent(id);

        log.info("Student with the name {} has been deleted", studentService.findById(id));
        studentService.delete(id);
    }

    @GetMapping(value = "api/student/find/{id}")
    public @ResponseBody Student findById(@PathVariable("id") UUID id) {
        checkIsEntityAbsent(id);

        log.info("Student with the name {} has been given", studentService.findById(id).getName());
        return studentService.findById(id);
    }

    //if entity (student or course) doesn't exist, it will throw exception in
    // validation (don't like the idea but that's the best I can think of)
    @PostMapping(value = "api/student/saveCourses", consumes = "application/json")
    public void saveStudentsCourse(@RequestBody @Validated StudentCourses courses, BindingResult errors) {
        if (errors.hasErrors()) throw STUDENTS_GRADE_IS_SMALL.exception();

        log.info("Student(s) have been assigned with total of {} courses", courses.getCoursesId().size());
        studentService.saveStudentsCourse(courses.getId(), courses.getCoursesId());
    }


    private void checkIsEntityAbsent(UUID id) {
        try {
            studentService.findById(id);
        } catch (NoSuchElementException e) {
            throw ENTITY_NOT_FOUND.exception(format("student with id {%s} does not exist", id));
        }
    }

   @ExceptionHandler(ApplicationError.ApplicationException.class)
   public ResponseEntity<ApplicationError.ApplicationException.ApplicationExceptionCompanion>
   handleApplicationException(ApplicationError.ApplicationException e) {
       return ResponseEntity.status(e.companion.code()).body(e.companion);
   }

    @ExceptionHandler(ValidationError.ValidationException.class)
    public ResponseEntity<ValidationError.ValidationException.ValidationExceptionCompanion>
    handleValidationException(ValidationError.ValidationException e) {
        return ResponseEntity.status(e.companion.code()).body(e.companion);
    }

}
