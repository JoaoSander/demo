/*
package com.example.demo.studentTest.controllerTest;

import com.example.demo.student.controller.StudentController;
import com.example.demo.student.model.Student;
import com.example.demo.student.service.StudentService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static java.time.Month.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.BDDAssumptions.given;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(StudentController.class)
class StudentControllerTest {

    String uri;

    public StudentControllerTest() {
    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    StudentService studentService;

    @BeforeTestClass
    public void setBaseUri() {
         uri = "http://localhost:" +8080;
    }

    @Test
    void getStudentsShouldReturnStatus200() throws Exception {

        List<Student> StudentsList = new ArrayList<>();

        StudentsList.add (
            new Student ("Joao", "joao@gmail.com", LocalDate.of(1999, MAY, 3))
        );

        StudentsList.add (
            new Student ("Maria", "Maria@gmail.com", LocalDate.of(2000, JUNE, 5))
        );

        when(studentService.getStudents()).thenReturn(StudentsList);

        List<Student> StudentsResult = Arrays.asList (
                given().port(8080).log().all()
                    .auth()
                    .basic("admin", "1234")
                    .contentType("application/json")
                .when().log().all()
                    .get(uri+"api/v1/student")
                .then().log().all()
                    .statusCode(HttpStatus.OK.value())
                    .extract()
                    .as(Student[].class)
        );

        assertThat(StudentsResult).isEqualTo(StudentsList);

    }

    @Test
    @Disabled
    void addNewStudent() {
    }

    @Test
    @Disabled
    void deleteStudent() {
    }

    @Test
    @Disabled
    void updateStudent() {
    }
}
*/