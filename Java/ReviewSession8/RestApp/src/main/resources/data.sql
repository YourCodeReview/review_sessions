CREATE TABLE courses (
    id            UUID    PRIMARY KEY,
    name          VARCHAR,
    description   VARCHAR,
    requiredGrade INT
);
CREATE TABLE students (
    id       UUID    PRIMARY KEY,
    name     VARCHAR,
    age      INT,
    grade    INT
);
CREATE TABLE student_course (
    studentId  UUID, FOREIGN KEY(studentId) REFERENCES students(id),
    courseId   UUID, FOREIGN KEY(courseId) REFERENCES courses(id)
);
