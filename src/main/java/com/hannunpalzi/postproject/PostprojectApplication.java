package com.hannunpalzi.postproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PostprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(PostprojectApplication.class, args);
    }

}
