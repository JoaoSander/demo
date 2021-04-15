package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

import static java.time.Month.*;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            Student joao = new Student(
                    "Joao",
                    "joao@gmail.com",
                    "12345678900",
                    LocalDate.of(1999, MAY, 3)
            );

            Student maria = new Student(
                    "Maria",
                    "maria@gmail.com",
                    "12345678901",
                    LocalDate.of(2000, JUNE, 5)
            );

            repository.saveAll(
                    List.of(joao, maria)
            );

        };
    }
}
