package com.hkust;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@ComponentScan(basePackages = {"com.hkust.controller.CabinetVO","com.hkust.controller.management"})
public class HkustScApplication {
    public static void main(String[] args) {
        SpringApplication.run(HkustScApplication.class, args);
    }
}