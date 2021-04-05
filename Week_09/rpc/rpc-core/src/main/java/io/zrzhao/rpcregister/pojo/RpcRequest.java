package io.zrzhao.rpcregister.pojo;

import lombok.Data;

/**
 * rpc请求封装
 *
 * @author zrzhao
 * @date 2021/3/27
 */
@Data
public class RpcRequest {

    private String clazzName;
    private String methodName;
    private Object[] args;

}
