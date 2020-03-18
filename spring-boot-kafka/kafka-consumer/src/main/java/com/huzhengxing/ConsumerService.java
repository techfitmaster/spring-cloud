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


    @KafkaListener(id = "foo", topics = "myTopic")
    public void listen(String data) {
      log.info("接收消息:{}",data);
    }


}
