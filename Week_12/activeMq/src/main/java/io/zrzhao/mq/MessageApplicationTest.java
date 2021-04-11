package io.zrzhao.mq;

import io.zrzhao.mq.msgqueue.ActiveMessageQueue;
import io.zrzhao.mq.msgqueue.MessageQueue;

/**
 * @author zrzhao
 * @date 2021/4/11
 */
public class MessageApplicationTest {

    public static void main(String[] args) {
        MessageQueue messageQueue = new ActiveMessageQueue();

        messageQueue.run();

        messageQueue.sendMsg("test send" + Thread.currentThread().getId());

        messageQueue.notify(message -> System.out.println("receive:" + message));

        messageQueue.stop();
    }

}
