package com.zrzhao.gateway.outbound;

import com.zrzhao.gateway.client.HttpClientUtil;
import com.zrzhao.gateway.filter.chain.HttpResponseFilterChain;
import com.zrzhao.gateway.registry.Registry;
import com.zrzhao.gateway.router.RouterUtils;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;
import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * 输出请求处理
 *
 * @author zrzhao
 */
public class HttpOutboundHandler {

    private static final Logger logger = LoggerFactory.getLogger(HttpOutboundHandler.class);

    private final HttpResponseFilterChain filterChain;

    public HttpOutboundHandler(HttpResponseFilterChain filterChain) {
        this.filterChain = filterChain;
    }

    public void handle(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        String router = RouterUtils.getInstance().getRouter().route(Registry.getInstance().getRegistered());

        String responseMsg = HttpClientUtil.getInstance().requestGet(router);

        FullHttpResponse response = null;
        try {
            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(responseMsg.getBytes("UTF-8")));
            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length", response.content().readableBytes());

            filterChain.filter(response, ctx);
        } catch (Exception e) {
            logger.info("处理出错", e);
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
        } finally {
            if (fullRequest != null) {
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    response.headers().set(CONNECTION, KEEP_ALIVE);
                    ctx.write(response);
                }
            }
            ctx.flush();
        }
    }


}
