package io.zrzhao.mq.msgqueue;

/**
 * 定义消息队列行为
 *
 * @author zrzhao
 * @date 2021/4/11
 */
public interface MessageQueue {

    /**
     * 启动
     */
    void run();

    /**
     * 停止
     */
    void stop();

    /**
     * 发送消息
     *
     * @param msg 消息内容
     * @return 是否成功
     */
    boolean sendMsg(String msg);

    /**
     * 接收消息提醒
     *
     * @param handler
     */
    void notify(MessageReceiveHandler handler);

}
