package io.zrzhao.server.pojo;

import lombok.Data;

/**
 * @author zrzhao
 * @date 2021/4/17
 */
@Data
public class MessageConsumerOffset {

    private String host;
    private String topic;
    private int offset;

    public MessageConsumerOffset(String host, String topic) {
        this.host = host;
        this.topic = topic;
        this.offset = 0;
    }

    public synchronized int next() {
        return this.offset++;
    }

}
