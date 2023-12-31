package com.team2.animalshelter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AnimalShelterTgBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnimalShelterTgBotApplication.class, args);
    }

}
