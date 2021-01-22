package com.zrzhao.gateway.filter.chain;

import com.zrzhao.gateway.filter.AbstractHttpRequestFilter;
import com.zrzhao.gateway.filter.HeaderHttpRequestFilter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * 责任链
 *
 * @author zrzhao
 * @date 2021/1/22
 */
public class HttpRequestFilterChain {

    /**
     * head of chain
     */
    private AbstractHttpRequestFilter head;
    /**
     * tail of chain
     */
    private AbstractHttpRequestFilter tail;

    public HttpRequestFilterChain() {
        addFilter(new HeaderHttpRequestFilter());
    }

    /**
     * append node
     *
     * @param requestFilter    node
     * @return
     */
    public HttpRequestFilterChain addFilter(AbstractHttpRequestFilter requestFilter) {
        if (head == null) {
            head = requestFilter;
            tail = requestFilter;
            return this;
        }
        tail.setSuccessor(requestFilter);
        tail = requestFilter;
        return this;
    }

    /**
     * filter
     *
     * @param fullRequest
     * @param ctx
     */
    public boolean filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        if (head == null) {
            return true;
        }
        return head.doFilter(fullRequest, ctx);
    }

}
