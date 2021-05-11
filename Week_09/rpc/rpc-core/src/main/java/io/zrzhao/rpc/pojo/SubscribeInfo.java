package io.zrzhao.rpc.pojo;

import lombok.Data;

/**
 * @author zrzhao
 * @date 2021/3/27
 */
@Data
public class SubscribeInfo {

    /**
     * ip
     */
    private String ip;

    /**
     * 端口
     */
    private int port;

}
