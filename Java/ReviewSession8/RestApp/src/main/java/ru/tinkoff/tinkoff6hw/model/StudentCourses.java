package ru.tinkoff.tinkoff6hw.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tinkoff.tinkoff6hw.validation.StudentConstraint;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@StudentConstraint
public class StudentCourses {
    private UUID id;
    private List<UUID> coursesId;
}
