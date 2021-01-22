package com.zrzhao.gateway.filter;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import org.apache.commons.lang3.StringUtils;

/**
 * 请求头过滤器
 *
 * @author zrzhao
 * @date 2021/1/22
 */
public class HeaderHttpRequestFilter extends AbstractHttpRequestFilter {

    @Override
    public boolean filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        String authorization = fullRequest.headers().get("authorization");
        return StringUtils.isNotBlank(authorization);
    }
}
