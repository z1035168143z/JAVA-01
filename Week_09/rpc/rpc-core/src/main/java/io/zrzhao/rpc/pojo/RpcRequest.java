package io.zrzhao.rpc.pojo;

import lombok.Data;

import java.util.concurrent.atomic.AtomicLong;

/**
 * rpc请求封装
 *
 * @author zrzhao
 * @date 2021/3/27
 */
@Data
public class RpcRequest {

    private long requestId;
    private String clazzName;
    private String methodName;
    private String methodSign;
    private Object[] args;

    static AtomicLong requestIdPool = new AtomicLong(1);

    public RpcRequest(){
        requestId = requestIdPool.incrementAndGet();
    }

}
