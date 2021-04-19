package com.example.demo.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static java.time.Month.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void CheckIfStudentEmailExists() {

        //given
        Student student = new Student(
                "Joao",
                "joao@gmail.com",
                LocalDate.of(1999, MAY, 3)
        );
        underTest.save(student);

        //when
        Optional<Student> expected = underTest.findStudentByEmail(student.getEmail());

        //then
        assertThat(expected.isPresent()).isTrue();
    }

    @Test
    void CheckIfStudentIdExists() {

        //given
        Student student = new Student(
                "Joao",
                "joao@gmail.com",
                LocalDate.of(1999, MAY, 3)
        );
        underTest.save(student);

        //when
        boolean expected = underTest.existsById(student.getId());

        //then
        assertThat(expected).isTrue();
    }


}