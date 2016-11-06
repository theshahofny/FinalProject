package com.ssa.ironyard.fitness;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FitnessAppStarter {

    static final Logger LOGGER = LogManager.getLogger(FitnessAppStarter.class);

    public static void main(String[] args) {
        LOGGER.info("Starting Fitness App");
        SpringApplication.run(FitnessAppStarter.class, args);
    }

}
