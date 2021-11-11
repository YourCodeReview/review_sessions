package ru.tinkoff.tinkoff6hw.service;

import org.springframework.stereotype.Service;
import ru.tinkoff.tinkoff6hw.dao.CourseRepository;
import ru.tinkoff.tinkoff6hw.model.Course;

import java.util.List;
import java.util.UUID;

@Service
public class CourseService {

    private final CourseRepository repository;
    public CourseService(CourseRepository courseRepository) { this.repository = courseRepository; }

    public void save(Course course) { repository.save(course); }

    public void update(Course newCourse) {
        repository.update(newCourse.getId(), newCourse);
    }

    public void delete(UUID id) { repository.delete(id); }

    public Course findById(UUID id) { return repository.findById(id).orElseThrow(); }
}