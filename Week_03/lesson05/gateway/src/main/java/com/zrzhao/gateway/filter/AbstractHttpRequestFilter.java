package com.zrzhao.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * 请求过滤器
 *
 * @author zrzhao
 * @date 2021/1/22
 */
public abstract class AbstractHttpRequestFilter {

    /**
     * 链表下一节点
     */
    private AbstractHttpRequestFilter successor;

    /**
     * 设置链表下一节点
     *
     * @param successor
     */
    public void setSuccessor(AbstractHttpRequestFilter successor) {
        this.successor = successor;
    }

    public boolean doFilter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        boolean pass = filter(fullRequest, ctx);
        if (!pass) {
            return false;
        }
        if (successor != null) {
            return successor.doFilter(fullRequest, ctx);
        }
        return true;
    }

    /**
     * 具体处理
     *
     * @param fullRequest
     * @param ctx
     * @return
     */
    public abstract boolean filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx);

}
