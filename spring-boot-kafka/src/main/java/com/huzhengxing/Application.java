package com.huzhengxing;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: Albert
 * @Date: 2020/3/3 14:21
 * @Contact: 1092144169@qq.com
 * @Description:
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

    @Autowired
    private KafkaTemplate<String,String> template;

    private final CountDownLatch latch = new CountDownLatch(3);


    @Override
    public void run(String... args) throws Exception {
        this.template.send("myTopic","msg1");
        this.template.send("myTopic","msg2");
        this.template.send("myTopic","msg3");
        latch.await(60, TimeUnit.SECONDS);
    }


    @KafkaListener(topics = {"myTopic"})
    public void listen(ConsumerRecord cr){
        System.out.println(cr.toString());
        latch.countDown();
    }
}
