package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents(){
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        Optional<Student> StudentOptionalEmail = studentRepository.findStudentByEmail(student.getEmail());
        Optional<Student> StudentOptionalCPF = studentRepository.findStudentByCPF(student.getCPF());
        if(StudentOptionalEmail.isPresent() || StudentOptionalCPF.isPresent()) {
            if(StudentOptionalEmail.isPresent()) {
                throw new IllegalStateException("Email: " +student.getEmail()+ " taken");
            } else {
                throw new IllegalStateException("CPF: " +student.getCPF()+ " already in use");
            }
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if(!exists) {
            throw new IllegalStateException("Student with id " +studentId+ " does not exists");
        }
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new IllegalStateException("student with Id " +studentId+ " does not exists")
        );

        if (name != null && name.length()>0 && !Objects.equals(student.getName(), name)) {
            student.setName(name);
        }

        if (email != null && email.length()>0 && !Objects.equals(student.getEmail(), email)) {
            student.setEmail(email);
        }
    }
}