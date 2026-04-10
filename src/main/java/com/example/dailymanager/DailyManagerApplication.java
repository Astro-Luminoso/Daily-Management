package com.example.dailymanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DailyManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DailyManagerApplication.class, args);
    }

}
