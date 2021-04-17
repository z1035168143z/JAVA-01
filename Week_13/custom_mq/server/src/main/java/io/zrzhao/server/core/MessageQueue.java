package io.zrzhao.server.core;

import io.zrzhao.server.pojo.Message;
import io.zrzhao.server.pojo.MessageConsumerOffset;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author zrzhao
 * @date 2021/4/17
 */
public class MessageQueue {

    private final String topic;
    private int capacity;
    private Message[] queue;
    private int writeIndex;

    private final Map<String, MessageConsumerOffset> offsetMap = new ConcurrentHashMap<>(16);

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public MessageQueue(String topic, int capacity) {
        this.topic = topic;
        this.capacity = capacity;
        this.queue = new Message[capacity];
    }

    public boolean send(Message message) {
        lock.writeLock().lock();
        this.queue[writeIndex++] = message;
        lock.writeLock().unlock();
        return true;
    }

    public Message poll(String host, Integer offset) {
        lock.readLock().lock();

        MessageConsumerOffset consumerOffset = offsetMap.get(host);
        if (consumerOffset == null) {
            consumerOffset = new MessageConsumerOffset(host, this.topic);
            offsetMap.put(host, consumerOffset);
        }
        if (offset != null) {
            consumerOffset.setOffset(offset);
        }

        Message message = this.queue[consumerOffset.getOffset()];
        if (message != null) {
            consumerOffset.next();
        }
        lock.readLock().unlock();

        return message;
    }

}
