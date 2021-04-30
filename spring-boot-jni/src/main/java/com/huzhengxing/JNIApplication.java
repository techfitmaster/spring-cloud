package com.huzhengxing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author - 2021/2/26 18:10 albert
 * @version - 2021/2/26 18:10 1.0.0
 * @file JNIApplication
 * @brief 文件简要说明
 * @par 其他重要信息：
 * @warning 警告信息:
 * @par 版权信息：
 * ©2019 杭州锘崴信息科技有限公司 版权所有
 */
@SpringBootApplication
public class JNIApplication {
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(JNIApplication.class, args);

        new HelloWorldJNI().sayHello();
    }
}
