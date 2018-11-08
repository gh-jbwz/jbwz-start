package com.jbwz.lemon.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.jbwz.lemon.server.entity")
public class LemonApplication {

    public static void main(String[] args) {
        SpringApplication.run(LemonApplication.class, args);
    }
}
