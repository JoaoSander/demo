package com.example.demo.security;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class BasicAuthIntegrationTest {

    @Test
    public void whenCorrectUsernameAndPasswordShouldReturn200(){
        RestAssured.given().auth()
                .basic("admin", "password")
                .when()
                .get("http://localhost:8080/api/v1/student")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void whenWrongUsernameOrPasswordShouldReturn401(){
        RestAssured.given().auth()
                .basic("admin", "passwor")
                .when()
                .get("http://localhost:8080/api/v1/student")
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void basicAuthenticationTestWhenStatusCode404(){
        RestAssured.given().auth()
                .basic("admin", "password")
                .when()
                .get("http://localhost:8080/api/v1/studentstudent")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

}