package io.zrzhao.rpcregister.pojo;

import lombok.Data;

/**
 * provider 注册信息
 *
 * @author zrzhao
 * @date 2021/3/27
 */
@Data
public class ProviderInfo {

    /**
     * ip
     */
    private String ip;

    /**
     * 端口
     */
    private int port;

    /**
     * class名称
     */
    private String clazzName;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 方法签名
     */
    private String methodSign;


}
