package com.huzhengxing.grpcserver.service.impl;

import com.huzhengxing.grpcinterface.api.HelloWorldGrpc;
import com.huzhengxing.grpcinterface.api.HelloWorldService;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author - 2022/1/17 18:17 albert
 * @version - 2022/1/17 18:17 1.0.0
 * @file HelloServiceImpl
 * @brief 文件简要说明
 * @par 其他重要信息：
 * @warning 警告信息:
 * @par 版权信息：
 * ©2019 杭州锘崴信息科技有限公司 版权所有
 */
@Slf4j
@Component
public class HelloServiceImpl extends HelloWorldGrpc.HelloWorldImplBase {

    @Override
    public void sayHello(HelloWorldService.HelloRequest request, StreamObserver<HelloWorldService.HelloResponse> responseObserver) {
        log.info("Input name is {}", request.getName());
        String message;
        request.getName();
        message = request.getName();

        HelloWorldService.HelloResponse response = HelloWorldService.HelloResponse.newBuilder().setMessage(message).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }
}
