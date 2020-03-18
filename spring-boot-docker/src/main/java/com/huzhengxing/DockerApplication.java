package com.huzhengxing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Auther: zhengxing.hu
 * @Date: 2020/3/18 20:10
 * @Description:
 */
@SpringBootApplication
public class DockerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DockerApplication.class,args);
        System.out.println("----------启动成功-------------");
    }
}
