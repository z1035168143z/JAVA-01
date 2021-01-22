package com.zrzhao.gateway.filter.chain;

import com.zrzhao.gateway.filter.AbstractHttpRequestFilter;
import com.zrzhao.gateway.filter.AbstractHttpResponseFilter;
import com.zrzhao.gateway.filter.HeaderHttpResponseFilter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

/**
 * 责任链
 *
 * @author zrzhao
 * @date 2021/1/22
 */
public class HttpResponseFilterChain {

    /**
     * head of chain
     */
    private AbstractHttpResponseFilter head;
    /**
     * tail of chain
     */
    private AbstractHttpResponseFilter tail;

    public HttpResponseFilterChain() {
        addFilter(new HeaderHttpResponseFilter());
    }

    /**
     * append node
     *
     * @param requestFilter    node
     * @return
     */
    public HttpResponseFilterChain addFilter(AbstractHttpResponseFilter requestFilter) {
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
     * @param fullResponse
     * @param ctx
     */
    public boolean filter(FullHttpResponse fullResponse, ChannelHandlerContext ctx) {
        if (head == null) {
            return true;
        }
        return head.doFilter(fullResponse, ctx);
    }

}
