package com.huzhengxing;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 2020/9/9 15:40  zhengxing.hu
 * @version 1.0.0
 * @file Controller
 * @brief
 * @par
 * @warning
 * @par 杭州锘崴信息科技有限公司版权所有©2020版权所有
 */
@RestController
public class Controller {

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
