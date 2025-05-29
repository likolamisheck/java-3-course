package com.taskmanager.taskapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@EnableAsync
public class TaskappApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskappApplication.class, args);
    }
}
