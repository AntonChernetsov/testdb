package com.example.mytestdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
public class MytestdbApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(MytestdbApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }

    private static Class<MytestdbApplication> applicationClass = MytestdbApplication.class;

}

