package io.zrzhao.server.core;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 存放 topic - queue
 *
 * @author zrzhao
 * @date 2021/4/17
 */
@Component
public class MessageBroker {

    /**
     * all queue
     */
    private final Map<String, MessageQueue> messageQueueMap = new ConcurrentHashMap<>(64);

    private final int capacity = 1024;

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * queue 不存在 自动创建
     *
     * @param topic
     * @return
     */
    public MessageQueue findQueueOnNotExistCreate(String topic) {
        MessageQueue messageQueue = messageQueueMap.get(topic);
        if (messageQueue != null) {
            return messageQueue;
        }

        lock.writeLock().lock();
        messageQueue = messageQueueMap.get(topic);
        try {
            if (messageQueue != null) {
                return messageQueue;
            }
            messageQueue = new MessageQueue(topic, capacity);
            messageQueueMap.put(topic, messageQueue);

            return messageQueue;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public MessageQueue findQueue(String topic) {
        return messageQueueMap.get(topic);
    }


}
