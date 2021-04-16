package com.example.demo.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.Optional;

import static java.time.Month.MAY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;
    private StudentService underTest;

    @BeforeEach
    void setUp() {
        underTest = new StudentService(studentRepository);
    }

    @Test
    void canGetAllStudents() {
        //when
        underTest.getStudents();

        //then
        verify(studentRepository).findAll();
    }

    @Test
    void canAddNewStudent() {
        //given
        Student student = new Student(
                "Joao",
                "joao@gmail.com",
                "12345678900",
                LocalDate.of(1999, MAY, 3)
        );

        //when
        underTest.addNewStudent(student);

        //then
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);

        verify(studentRepository).save(studentArgumentCaptor.capture());

        Student capturedStudent = studentArgumentCaptor.getValue();

        assertThat(capturedStudent).isEqualTo(student);
    }

    @Test
    void willThrowWhenEmailIsTaken() {

        //given
        Student student = new Student(
                "Joao",
                "joao@gmail.com",
                "12345678900",
                LocalDate.of(1999, MAY, 3)
        );
        studentRepository.save(student);

        //when  **********ERRO**********
        given(studentRepository.findStudentByEmail(student.getEmail()).isPresent()).willReturn(true);
        /*optional nao retorna boolean*/

        //then
        assertThatThrownBy(() -> underTest.addNewStudent(student))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Email: " +student.getEmail()+ " taken");
    }

    @Test
    @Disabled
    void canDeleteStudent(Long studentId) {
    }

    @Test
    void willThrownWhenIdDoesNotExists() {

        //given
        Student student = new Student(
                "Joao",
                "joao@gmail.com",
                "12345678900",
                LocalDate.of(1999, MAY, 3)
        );
        studentRepository.save(student);

        //when
        given(studentRepository.existsById(student.getId())).willReturn(false);

        //
        assertThatThrownBy(() -> underTest.deleteStudent(student.getId()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Student with id " +student.getId()+ " does not exists");

    }

    @Test
    @Disabled
    void canUpdateStudent() {
    }
}