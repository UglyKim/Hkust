package com.hkust;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackages = {"com.hkust.controller.CabinetVO","com.hkust.controller.management"})
public class CabinetApplication {
    public static void main(String[] args) {
        SpringApplication.run(CabinetApplication.class, args);
    }
}