package ru.tinkoff.tinkoff6hw.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.tinkoff6hw.exception.ApplicationError;
import ru.tinkoff.tinkoff6hw.exception.ValidationError;
import ru.tinkoff.tinkoff6hw.model.Course;
import ru.tinkoff.tinkoff6hw.service.CourseService;

import javax.validation.Valid;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

import static java.lang.String.format;
import static ru.tinkoff.tinkoff6hw.exception.ApplicationError.ENTITY_NOT_FOUND;
import static ru.tinkoff.tinkoff6hw.exception.ValidationError.ENTITY_IS_NULL;

@RestController
public class CourseController {

    private static final Logger log = LoggerFactory.getLogger(CourseController.class);

    private final CourseService courseService;
    public CourseController(CourseService service) { this.courseService = service; }

    @PostMapping(value = "api/course/save", consumes = "application/json")
    public void saveCourse(@RequestBody @Validated Course course, BindingResult errors) {
        if(errors.hasErrors()) throw ENTITY_IS_NULL.exception();

        courseService.save(course);
        log.info("New Course with the name {} has been added", course.getName());
    }

    @PutMapping(value = "api/course/update", consumes = "application/json")
    public void updateCourse(@RequestBody @Valid Course course) {
        checkIsEntityAbsent(course.getId());

        courseService.update(course);
        log.info("Course with the name {} has been updated", course.getName());
    }

    //many browsers ignore the entity body with delete method, so it's done via id
    @DeleteMapping(value = "api/course/delete/{id}")
    public void deleteCourse(@PathVariable("id") UUID id) {
        checkIsEntityAbsent(id);

        log.info("Course with the name {} has been deleted", courseService.findById(id));
        courseService.delete(id);
    }

    @GetMapping(value = "api/course/find/{id}")
    public @ResponseBody Course findById(@PathVariable("id") UUID id) {
        checkIsEntityAbsent(id);

        log.info("Course with the name {} has been given", courseService.findById(id).getName());
        return courseService.findById(id);
    }

    private void checkIsEntityAbsent(UUID id) {
        try {
            courseService.findById(id);
        } catch (NoSuchElementException e) {
            throw ENTITY_NOT_FOUND.exception(format("course with id {%s} does not exist", id));
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
