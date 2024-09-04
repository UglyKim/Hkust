package com.hkust;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HkustMcApplication {
    public static void main(String[] args) {
        System.setProperty("channel", "mc");
        SpringApplication.run(HkustMcApplication.class, args);
    }
}
