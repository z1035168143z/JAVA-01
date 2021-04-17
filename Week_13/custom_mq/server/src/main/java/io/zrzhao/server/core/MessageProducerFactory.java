package io.zrzhao.server.core;

import io.zrzhao.server.pojo.Message;

/**
 * @author zrzhao
 * @date 2021/4/17
 */
public class MessageProducerFactory {

    private MessageBroker broker;

    public MessageProducerFactory(MessageBroker broker) {
        this.broker = broker;
    }

    public boolean send(String topic, Message message) {
        MessageQueue queue = this.broker.findQueueOnNotExistCreate(topic);
        return queue.send(message);
    }

}
