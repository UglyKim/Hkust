package com.hkust;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WmcApplication {
    public static void main(String[] args) {
        System.setProperty("channel", "mc");
        SpringApplication.run(WmcApplication.class, args);
    }
}
