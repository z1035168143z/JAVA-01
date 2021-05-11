package io.zrzhao.rpc.handler;

import io.zrzhao.rpc.connection.ConsumerConnection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author zrzhao
 * @date 2021/5/10
 */
public class RpcConsumerInvokeHandler implements InvocationHandler {


    private ConsumerConnection consumerConnection;

    public RpcConsumerInvokeHandler(ConsumerConnection consumerConnection) {
        this.consumerConnection = consumerConnection;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
        return consumerConnection.send(method, args);
    }
}
