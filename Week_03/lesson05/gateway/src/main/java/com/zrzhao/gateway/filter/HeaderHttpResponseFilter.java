package com.zrzhao.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import org.apache.commons.lang3.StringUtils;

/**
 * 请求头过滤器
 *
 * @author zrzhao
 * @date 2021/1/22
 */
public class HeaderHttpResponseFilter extends AbstractHttpResponseFilter {

    @Override
    public boolean filter(FullHttpResponse fullResponse, ChannelHandlerContext ctx) {
        fullResponse.headers().set("authorization", String.valueOf(System.nanoTime()));
        return true;
    }
}
