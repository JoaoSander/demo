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

import static java.time.Month.*;
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
                LocalDate.of(1999, MAY, 3)
        );
        studentRepository.save(student);

        //when
        given(studentRepository.findStudentByEmail(student.getEmail())).willReturn(Optional.of(student));
        given(studentRepository.existsById(student.getId())).willReturn(false);
        //then
        assertThatThrownBy(() -> underTest.addNewStudent(student))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Email: " +student.getEmail()+ " taken");

        /*assertThatThrownBy(() -> underTest.updateStudent(student.getId(), student.getName(), student.getEmail()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Email " +student.getEmail()+ " taken");*/
    }

    @Test
    void canDeleteStudent() {
        //given
        Student student = new Student(
                "Joao",
                "joao@gmail.com",
                LocalDate.of(1999, MAY, 3)
        );
        studentRepository.save(student);

        //when
        given(studentRepository.existsById(student.getId())).willReturn(true);
        underTest.deleteStudent(student.getId());

        //then
        verify(studentRepository).deleteById(student.getId());
    }

    @Test
    void willThrowWhenIdIsNotFound() {
        // given
        Student student = new Student (
                "Joao",
                "joao@gmail.com",
                LocalDate.of(1999, MAY, 3)
        );
        studentRepository.save(student);

        // when
        given(studentRepository.existsById(student.getId())).willReturn(false);

        // then
        assertThatThrownBy(() -> underTest.deleteStudent(student.getId()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Student with ID " +student.getId()+ " does not exists");

        assertThatThrownBy(() -> underTest.updateStudent(student.getId(), student.getName(), student.getEmail()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Student with ID " +student.getId()+ " does not exists");

    }

    /*@Test
    void willThrownWhenEmailIsNull() {
        // given
        Student student = new Student (
                "Joao",
                "joao@gmail.com",
                LocalDate.of(1999, MAY, 3)
        );
        studentRepository.save(student);

        // when
        given(studentRepository.findStudentByEmail(student.getEmail())).willReturn(null);

        assertThatThrownBy(() -> underTest.updateStudent(student.getId(), student.getName(), student.getEmail()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Email invalido");
    }*/

    @Test
    @Disabled
    void canUpdateStudent() {


    }

}