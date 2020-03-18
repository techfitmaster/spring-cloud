package com.huzhengxing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@EnableScheduling
public class ProducerService {


    @Autowired
    private AmqpAdmin amqpAdmin;

    @Autowired
    private AmqpTemplate amqpTemplate;

    int i = 0;

    @Scheduled(cron = "0/10 * * * * ? ")
    public void sendMessage() {
        log.info("发送消息-------------");
        amqpTemplate.convertAndSend("myQueue",i++);
    }

}
