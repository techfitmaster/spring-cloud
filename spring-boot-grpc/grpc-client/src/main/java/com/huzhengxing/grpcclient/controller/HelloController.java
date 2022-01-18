package com.huzhengxing.grpcclient.controller;

import com.huzhengxing.grpcclient.config.GrpcClientConfig;
import com.huzhengxing.grpcinterface.api.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author - 2022/1/17 19:00 albert
 * @version - 2022/1/17 19:00 1.0.0
 * @file HelloController
 * @brief 文件简要说明
 * @par 其他重要信息：
 * @warning 警告信息:
 * @par 版权信息：
 * ©2019 杭州锘崴信息科技有限公司 版权所有
 */
@RestController
public class HelloController {

    @Autowired
    private GrpcClientConfig grpcClientConfig;

    @GetMapping("/hello")
    public ResponseEntity<String> hello(String name) {
        HelloWorldService.HelloRequest request = HelloWorldService.HelloRequest.newBuilder().setName(name).build();
        HelloWorldService.HelloResponse response = grpcClientConfig.getStub().sayHello(request);
        return ResponseEntity.ok(response.getMessage());
    }

}
