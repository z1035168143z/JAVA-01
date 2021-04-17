package io.zrzhao.server.pojo;

import io.zrzhao.server.pojo.Message;
import lombok.Data;

/**
 * @author zrzhao
 * @date 2021/4/17
 */
public class MessageCourier {

    private String topic;
    private Message message;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
