package com.huzhengxing.grpcclient;

import com.huzhengxing.grpcclient.config.GrpcClientConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class GrpcClientApplication implements CommandLineRunner {

    @Autowired
    private GrpcClientConfig configuration;

    public static void main(String[] args) {
        SpringApplication.run(GrpcClientApplication.class, args);
    }

    public void run(String... args) throws Exception {
        configuration.start();
//        configuration.shutdown();
    }
}
