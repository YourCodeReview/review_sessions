package ru.tinkoff.tinkoff6hw.dao;

import org.apache.ibatis.annotations.Mapper;
import ru.tinkoff.tinkoff6hw.model.Student;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface StudentRepository {

    void save(Student student);

    void saveStudentsCourse(UUID studentId, UUID courseId);

    void update(UUID id, Student student);

    void delete(UUID id);

    Optional<Student> findById(UUID id);

    List<Student> findAll();
}