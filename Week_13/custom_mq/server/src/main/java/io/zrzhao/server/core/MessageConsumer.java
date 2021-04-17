package io.zrzhao.server.core;

import io.zrzhao.server.pojo.Message;

/**
 * 消费
 *
 * @author zrzhao
 * @date 2021/4/17
 */
public class MessageConsumer {

    private MessageBroker broker;
    private String topic;
    private String host;

    public MessageConsumer(MessageBroker broker, String topic, String host) {
        this.broker = broker;
        this.host = host;
        this.topic = topic;
    }

    public Message poll(Integer offset) {
        MessageQueue queue = this.broker.findQueue(topic);
        return queue.poll(host, offset);
    }

}
