package de.ityreh.home.blogservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the Blog Service.
 * 
 * This Spring Boot application manages a blog system with users, posts,
 * tags, and categories.
 */
@SpringBootApplication
public class BlogServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogServiceApplication.class, args);
    }
}
