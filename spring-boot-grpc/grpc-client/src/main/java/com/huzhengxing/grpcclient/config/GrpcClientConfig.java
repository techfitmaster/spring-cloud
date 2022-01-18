package com.huzhengxing.grpcclient.config;

import com.huzhengxing.grpcinterface.api.HelloWorldGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

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
public class GrpcClientConfig {

    /**
     * gRPC Server的地址
     */
    @Value("${grpc.server-host}")
    private String host;

    /**
     * gRPC Server的端口
     */
    @Value("${grpc.server-port}")
    private int port;

    private ManagedChannel channel;
    private HelloWorldGrpc.HelloWorldBlockingStub stub;

    public void start() {
        // 开启channel
        channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();

        // 通过channel获取到服务端的stub
        stub = HelloWorldGrpc.newBlockingStub(channel);
        log.info("gRPC client started, server address: {}:{}", host, port);
    }

    public void shutdown() throws InterruptedException {
        // 调用shutdown方法后等待1秒关闭channel
        channel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
        log.info("gRPC client shut down successfully.");
    }

    public HelloWorldGrpc.HelloWorldBlockingStub getStub() {
        return this.stub;
    }
}
