package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.NotFoundFacultyException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.*;

import java.util.*;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    public StudentService(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    public Student createStudent(Student student) {
        Long Id = student.getFaculty().getId();
        Optional<Faculty> facultyDB = facultyRepository.findById(Id);
        facultyDB.orElseThrow(() -> new NotFoundFacultyException("Отсутствует информация о факультете."));
        student.setFaculty(facultyDB.get());
        return studentRepository.save(student);
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).get();
    }

    public Student updateStudent(Student student) {
        Long Id = student.getFaculty().getId();
        Optional<Faculty> facultyDB = facultyRepository.findById(Id);
        facultyDB.orElseThrow(() -> new NotFoundFacultyException("Отсутствует информация о факультете."));
        student.setFaculty(facultyDB.get());
        return studentRepository.save(student);
    }

    public Student deleteStudent(Long id) {
        Student student = studentRepository.findById(id).get();
        studentRepository.deleteById(id);
        return student;
    }

    public Collection<Student> allStudentByAge(int age) {
        return Collections.unmodifiableCollection(studentRepository.findAllByAge(age));
    }

    public Collection<Student> allStudentByAgeBetween(int min, int max) {
        return Collections.unmodifiableCollection(studentRepository.findAllByAgeBetween(min, max));
    }

    public Faculty getFacultyById(Long id) {
        return studentRepository.findById(id).get().getFaculty();
    }

    public Collection<Student> allStudent() {
        return Collections.unmodifiableCollection(studentRepository.findAll());
    }

}
