package io.zrzhao.server.pojo;

import lombok.Data;

/**
 * @author zrzhao
 * @date 2021/4/17
 */
@Data
public class MessageReceive {

    private String host;
    private String topic;
    private Integer offset;

}
