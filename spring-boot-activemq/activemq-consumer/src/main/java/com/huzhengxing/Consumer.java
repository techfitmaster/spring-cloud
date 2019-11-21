package com.huzhengxing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @Auther: Albert
 * @Date: 2019/11/20 16:23
 * @Contact: 1092144169@qq.com
 * @Description:
 */
@Slf4j
@Component
public class Consumer {

    @JmsListener(destination = "${queue}")
    public void receive(String msg) {
        log.info("变更数据回到:{}",msg);
    }
}
