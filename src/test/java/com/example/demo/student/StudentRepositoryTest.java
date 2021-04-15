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
                "12345678900",
                LocalDate.of(1999, MAY, 3)
        );
        underTest.save(student);

        //when
        Optional<Student> expected = underTest.findStudentByEmail(student.getEmail());

        //then
        assertThat(expected.isPresent()).isTrue();

    }

    @Test
    void CheckIfStudentEmailDoesNotExists() {

        //given
        String email = "joaoFalse@gmail.com";

        //when
        Optional<Student> expected = underTest.findStudentByEmail(email);

        //then
        assertThat(expected.isPresent()).isFalse();

    }

    @Test
    void CheckIfStudentCpfExists() {
        //given
        Student student = new Student(
                "Joao",
                "joao@gmail.com",
                "12345678900",
                LocalDate.of(1999, MAY, 3)
        );
        underTest.save(student);

        //when
        Optional<Student> expected = underTest.findStudentByCPF(student.getCPF());

        //then
        assertThat(expected.isPresent()).isTrue();
    }

    @Test
    void CheckIfStudentCpfDoesNotExists() {
        //given
        String CPF = "12345678901";

        //when
        Optional<Student> expected = underTest.findStudentByCPF(CPF);

        //then
        assertThat(expected.isPresent()).isFalse();
    }

}