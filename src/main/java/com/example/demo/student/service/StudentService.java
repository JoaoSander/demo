package com.example.demo.student.service;

import com.example.demo.student.repository.StudentRepository;
import com.example.demo.student.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        log.info("Returning getStudents");
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long studentId) {
        Optional<Student> existsById = studentRepository.findById(studentId);
        if(existsById.isEmpty()) {
            throw new IllegalStateException("Student with ID " + studentId + " does not exists");
        }
        log.info("Returning getStudentById");
        return studentRepository.findById(studentId);
    }

    public Student addNewStudent(Student student) {
        Optional<Student> existsByEmail = studentRepository.findByEmail(student.getEmail());
        if (existsByEmail.isPresent()) {
            throw new IllegalStateException("Email: " + student.getEmail() + " taken");
        }
        log.info("Saving a new student");
        studentRepository.save(student);
        log.info("Returning: Student " + student.getName() + " added!");
        return student;
    }

    public Long deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if (!exists) {
            throw new IllegalStateException("Student with ID " + studentId + " does not exists");
        }
        log.info("Deleting student");
        studentRepository.deleteById(studentId);
        log.info("Returning: Student with id " + studentId + " deleted!");
        return studentId;
    }

    @Transactional
    public List<Student> updateStudent(Long studentId, String name, String email) {

        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new IllegalStateException("Student with ID " + studentId + " does not exists")
        );

        if (name!=null && name.length()>0) {
            log.info("Updating student name");
            student.setName(name);
        }

        if (email!=null && email.length()>0) {
            Optional<Student> existsByEmail = studentRepository.findByEmail(email);
            if (existsByEmail.isPresent()) {
                throw new IllegalStateException("Email: " +email+ " taken");
            }
            log.info("Updating student email");
            student.setEmail(email);
        }
        log.info("Returning: Student with id " + studentId + " updated!");
        return List.of(student);
    }

}