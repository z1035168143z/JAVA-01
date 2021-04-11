package io.zrzhao.mq.msgqueue;

import javax.jms.Message;

/**
 * @author zrzhao
 * @date 2021/4/11
 */
public interface MessageReceiveHandler {

    /**
     * 处理消息
     *
     * @param message
     */
    void handler(Message message);

}
