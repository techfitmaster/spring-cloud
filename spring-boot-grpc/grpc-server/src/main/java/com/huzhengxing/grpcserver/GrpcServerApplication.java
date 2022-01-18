package com.huzhengxing.grpcserver;

import com.huzhengxing.grpcserver.config.GrpcServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Component
public class GrpcServerApplication implements CommandLineRunner {

    @Autowired
    GrpcServerConfig configuration;

    public static void main(String[] args) {
        SpringApplication.run(GrpcServerApplication.class, args);
    }

    public void run(String... args) throws Exception {
        configuration.start();
//        configuration.block();
    }

}
