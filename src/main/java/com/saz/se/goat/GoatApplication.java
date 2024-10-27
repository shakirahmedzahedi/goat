package com.saz.se.goat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class GoatApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoatApplication.class, args);
    }

}
