package ru.tinkoff.tinkoff6hw.service;

import org.springframework.stereotype.Service;
import ru.tinkoff.tinkoff6hw.dao.StudentRepository;
import ru.tinkoff.tinkoff6hw.model.Student;

import java.util.List;
import java.util.UUID;

@Service
public class StudentService {

    private final StudentRepository repository;
    public StudentService(StudentRepository studentRepository) { this.repository = studentRepository; }

    public void save(Student student) {
        repository.save(student);
    }

    public void saveStudentsCourse(UUID id, List<UUID> course) {
        for (UUID value : course) {
            repository.saveStudentsCourse(id, value);
        }
    }

    public void update(Student newStudent) { repository.update(newStudent.getId(), newStudent); }

    public void delete(UUID id) { repository.delete(id); }

    public Student findById(UUID id) { return repository.findById(id).orElseThrow(); }
}
