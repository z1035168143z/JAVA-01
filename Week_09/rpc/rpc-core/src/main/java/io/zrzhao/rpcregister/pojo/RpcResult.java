package io.zrzhao.rpcregister.pojo;

import lombok.Data;

/**
 * rpc请求结果
 *
 * @author zrzhao
 * @date 2021/3/27
 */
@Data
public class RpcResult {

    /**
     * 请求状态
     */
    private int status;
    /**
     * 请求结果
     */
    private Object result;
    /**
     * 是否有异常抛出
     */
    private Throwable throwable;

    public RpcResult(int status, Object result) {
        this.status = status;
        this.result = result;
    }
}
