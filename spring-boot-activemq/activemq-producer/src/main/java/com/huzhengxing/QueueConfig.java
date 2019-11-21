package com.huzhengxing;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;

/**
 * @Auther: Albert
 * @Date: 2019/11/20 16:40
 * @Contact: 1092144169@qq.com
 * @Description: 配置一个队列
 */
@Configuration
public class QueueConfig {

    @Value("${queue}")
    private String queue;

    @Bean
    public Queue logQueue() {
        return new ActiveMQQueue(queue);
    }

}
