package io.zrzhao.server.core;

/**
 * @author zrzhao
 * @date 2021/4/17
 */
public class MessageOperatorFactory {

    private final MessageBroker broker;

    public MessageOperatorFactory(MessageBroker broker) {
        this.broker = broker;
    }

    public MessageProducer createProducer(String topic) {
        return new MessageProducer(broker, topic);
    }

    public MessageConsumer createConsumer(String host, String topic) {
        return new MessageConsumer(broker, topic, host);
    }

}
