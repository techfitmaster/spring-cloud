package com.huzhengxing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.Queue;


/**
 * @Auther: Albert
 * @Date: 2019/11/20 16:39
 * @Contact: 1092144169@qq.com
 * @Description:
 */
@Component
@EnableScheduling
@Slf4j
public class Producer {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;

    @Scheduled(fixedDelay = 10000)
    public void send() {
        String message = "发布消息:" + System.currentTimeMillis();
        log.info(message);
        jmsMessagingTemplate.convertAndSend(queue,message);
    }

}
