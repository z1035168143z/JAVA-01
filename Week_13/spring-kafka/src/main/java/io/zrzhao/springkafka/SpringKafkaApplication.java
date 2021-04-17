package io.zrzhao.springkafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

@SpringBootApplication
@Slf4j
public class SpringKafkaApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringKafkaApplication.class, args);

        KafkaTemplate kafkaTemplate = applicationContext.getBean(KafkaTemplate.class);
        kafkaTemplate.send("test-topic", "ceshixiaoxi");
    }

    @KafkaListener(topics = "test-topic")
    public void processMessage(String content) {
        log.info("收到消息, topic:test, msg:{}", content);
    }

}
