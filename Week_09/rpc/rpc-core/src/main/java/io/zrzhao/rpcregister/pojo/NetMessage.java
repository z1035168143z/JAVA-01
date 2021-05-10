package io.zrzhao.rpcregister.pojo;

import lombok.Data;

/**
 * 此次网络请求的信息
 *
 * @author zrzhao
 * @date 2021/3/27
 */
@Data
public class NetMessage {

    /**
     * 请求封装到哪个实体
     */
    private String className;

    /**
     * 具体的内容
     */
    private String content;

}
