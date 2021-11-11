package ru.tinkoff.tinkoff6hw.dao;

import org.apache.ibatis.annotations.Mapper;
import ru.tinkoff.tinkoff6hw.model.Course;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface CourseRepository {

    void save(Course course);

    void update(UUID id, Course course);

    void delete(UUID id);

    Optional<Course> findById(UUID id);

    List<Course> findAll();
}