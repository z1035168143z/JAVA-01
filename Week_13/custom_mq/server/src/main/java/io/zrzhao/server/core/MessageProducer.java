package io.zrzhao.server.core;

import io.zrzhao.server.pojo.Message;

/**
 * @author zrzhao
 * @date 2021/4/17
 */
public class MessageProducer {

    private MessageBroker broker;
    private String topic;

    public MessageProducer(MessageBroker broker, String topic) {
        this.broker = broker;
        this.topic = topic;
    }

    public boolean send(Message message) {
        MessageQueue queue = this.broker.findQueueOnNotExistCreate(topic);
        return queue.send(message);
    }

}
