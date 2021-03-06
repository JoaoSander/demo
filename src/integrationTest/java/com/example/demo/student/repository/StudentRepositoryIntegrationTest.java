package com.example.demo.student.repository;

import com.example.demo.student.model.Student;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.time.LocalDate;
import java.util.Optional;
import static java.time.Month.*;

@DataJpaTest
class StudentRepositoryIntegrationTest {

    @Autowired
    private StudentRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void checkIfStudentEmailExists() {
        //given
        Student student = new Student(
                "Joao",
                "joao@gmail.com",
                LocalDate.of(1999, MAY, 3)
        );
        underTest.save(student);

        //when
        Optional<Student> expected = underTest.findByEmail(student.getEmail());

        //then
        AssertionsForClassTypes.assertThat(expected.isPresent()).isTrue();
    }

    @Test
    void checkIfStudentEmailDoesNotExists() {
        //given
        Student student = new Student(
                "Joao",
                "joao@gmail.com",
                LocalDate.of(1999, MAY, 3)
        );
        underTest.save(student);

        String email = "joaoFalse@hotmail.com";

        //when
        Optional<Student> expected = underTest.findByEmail(email);

        //then
        AssertionsForClassTypes.assertThat(expected.isPresent()).isFalse();
    }

}