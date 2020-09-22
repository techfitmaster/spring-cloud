package com.huzhengxing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @Auther: Albert
 * @Date: 2020/3/3 15:35
 * @Contact: 1092144169@qq.com
 * @Description:
 */
@Component
@Slf4j
public class ConsumerService {


    @KafkaListener(topics = "DataNode")
    public void listen(String data) {
        log.info("接收消息 myTopic :{}", data);
    }

    @KafkaListener(topics = "MedusaDataSync")
    public void listen1(String data) {
        log.info("接收消息 myTopic1:{}", data);
    }

    @KafkaListener(topics = "User")
    public void listen2(String data) {
        log.info("接收消息 myTopic2:{}", data);
    }

    @KafkaListener(topics = "myTopic3")
    public void listen3(String data) {
        log.info("接收消息 myTopic :{}", data);
    }

    @KafkaListener(topics = "myTopic4")
    public void listen4(String data) {
        log.info("接收消息 myTopic1:{}", data);
    }

    @KafkaListener(topics = "myTopic5")
    public void listen5(String data) {
        log.info("接收消息 myTopic2:{}", data);
    }

    @KafkaListener(topics = "myTopic6")
    public void listen6(String data) {
        log.info("接收消息 myTopic1:{}", data);
    }

    @KafkaListener(topics = "myTopic7")
    public void listen7(String data) {
        log.info("接收消息 myTopic7:{}", data);
    }


}
