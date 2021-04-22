package com.example.demo.student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.Optional;

import static java.time.Month.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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
        given(studentRepository.findById(student.getId())).willReturn(Optional.of(student));
        given(studentRepository.findStudentByEmail(student.getEmail())).willReturn(Optional.of(student));

        //then
        assertThatThrownBy(() -> underTest.addNewStudent(student))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Email: " + student.getEmail() + " taken");

        assertThatThrownBy(() -> underTest.updateStudent(student.getId(), student.getName(), student.getEmail()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Email: " + student.getEmail() + " taken");
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
    void canUpdateStudent() {
        //given
        Student student = new Student(
                "Joao",
                "joao@gmail.com",
                LocalDate.of(1999, MAY, 3)
        );
        studentRepository.save(student);

        String email = "joaozinho@gmail.com";
        String nome = "Joao";

        given(studentRepository.findById(student.getId())).willReturn(Optional.of(student));

        underTest.updateStudent(student.getId(), nome, email);

        assertThat(student.getName()).isEqualTo(nome);
        assertThat(student.getEmail()).isEqualTo(email);
    }
}