package com.huzhengxing.springboodapusic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringBoodApusicApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringBoodApusicApplication.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(SpringBoodApusicApplication.class, args);
    }


    @GetMapping
    public String test() {
        return "Request success";
    }
}
