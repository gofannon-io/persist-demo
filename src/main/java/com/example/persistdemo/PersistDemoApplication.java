package com.example.persistdemo;

import com.example.persistdemo.cat.CatRepository;
import com.example.persistdemo.cat.CatRest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = {AbstractEntity.class, CatRepository.class, CatRest.class})
public class PersistDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersistDemoApplication.class, args);
    }

}
