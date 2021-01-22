package com.zrzhao.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

/**
 * 请求过滤器
 *
 * @author zrzhao
 * @date 2021/1/22
 */
public abstract class AbstractHttpResponseFilter {

    /**
     * 链表下一节点
     */
    private AbstractHttpResponseFilter successor;

    /**
     * 设置链表下一节点
     *
     * @param successor
     */
    public void setSuccessor(AbstractHttpResponseFilter successor) {
        this.successor = successor;
    }

    public boolean doFilter(FullHttpResponse fullResponse, ChannelHandlerContext ctx) {
        boolean pass = filter(fullResponse, ctx);
        if (!pass) {
            return false;
        }
        if (successor != null) {
            return successor.doFilter(fullResponse, ctx);
        }
        return false;
    }

    /**
     * 具体处理
     *
     * @param fullResponse
     * @param ctx
     * @return
     */
    public abstract boolean filter(FullHttpResponse fullResponse, ChannelHandlerContext ctx);

}
