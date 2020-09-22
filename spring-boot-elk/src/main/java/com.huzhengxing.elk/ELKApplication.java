package com.huzhengxing.elk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: zhengxing.hu
 * @Date: 2020/5/18 14:43
 * @Description:
 */
@SpringBootApplication
@RestController
@RequestMapping()
public class ELKApplication {
    public static void main(String[] args) {
        SpringApplication.run(ELKApplication.class, args);
    }

    @GetMapping("test")
    public String  test() {
        String str = null;
        System.out.println(str.length());
        int i = 1/0;
        return "test";
    }

    /**
     * 查询
     * @return
     */
    @GetMapping("test1")
    public String  test1() {
        return "test1";
    }
}
