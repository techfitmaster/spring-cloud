package com.huzhengxing;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @Auther: Albert
 * @Date: 2020/3/3 15:47
 * @Contact: 1092144169@qq.com
 * @Description:
 */
@Component
@EnableScheduling
@Slf4j
public class ProducerService {

    @Autowired
    private KafkaAdmin admin;

    @Autowired
    private KafkaTemplate template;

    int i = 0;
    @Scheduled(cron = "0/10 * * * * ? ")
    public void sendMessage() {
        log.info("发送消息-----------------");

        ListenableFuture<SendResult<Integer, String>> future = this.template.send("myTopic","something" + (i++));
        future.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                log.info("成功回调：{}", JSONObject.toJSONString(result));
            }

            @Override
            public void onFailure(Throwable ex) {
                log.info("失败回调------------");
            }

        });
    }



}
