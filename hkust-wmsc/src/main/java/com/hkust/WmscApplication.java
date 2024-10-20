package com.hkust;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WmscApplication {
    public static void main(String[] args) {
        System.setProperty("channel", "sc");
        SpringApplication.run(WmscApplication.class, args);
    }
}
