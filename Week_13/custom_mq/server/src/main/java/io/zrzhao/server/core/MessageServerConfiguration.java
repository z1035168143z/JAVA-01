package io.zrzhao.server.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zrzhao
 * @date 2021/4/17
 */
@Configuration
public class MessageServerConfiguration {

    @Autowired
    private MessageBroker broker;

    @Bean
    public MessageOperatorFactory messageConsumerFactory() {
        return new MessageOperatorFactory(broker);
    }

}
