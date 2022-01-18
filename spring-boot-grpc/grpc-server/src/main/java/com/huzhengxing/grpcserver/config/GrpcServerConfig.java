package com.huzhengxing.grpcserver.config;

import com.huzhengxing.grpcserver.service.impl.HelloServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author - 2022/1/17 18:31 albert
 * @version - 2022/1/17 18:31 1.0.0
 * @file GrpcServerConfig
 * @brief 文件简要说明
 * @par 其他重要信息：
 * @warning 警告信息:
 * @par 版权信息：
 * ©2019 杭州锘崴信息科技有限公司 版权所有
 */
@Slf4j
@Component
public class GrpcServerConfig {

    @Autowired
    HelloServiceImpl service;

    /**
     * 注入配置文件中的端口信息
     */
    @Value("${grpc.server-port}")
    private int port;
    private Server server;

    public void start() throws IOException {
        // 构建服务端
        log.info("Starting gRPC on port {}.", port);
        server = ServerBuilder.forPort(port).addService(service).build().start();
        log.info("gRPC server started, listening on {}.", port);

        // 添加服务端关闭的逻辑
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Shutting down gRPC server.");
            GrpcServerConfig.this.stop();
            log.info("gRPC server shut down successfully.");
        }));
    }

    private void stop() {
        if (server != null) {
            // 关闭服务端
            server.shutdown();
        }
    }

    public void block() throws InterruptedException {
        if (server != null) {
            // 服务端启动后直到应用关闭都处于阻塞状态，方便接收请求
            server.awaitTermination();
        }
    }
}
