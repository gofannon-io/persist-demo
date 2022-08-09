package com.example.persistdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = {PersistDemoApplication.class})
public class PersistDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersistDemoApplication.class, args);
    }

}
