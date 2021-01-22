package com.zrzhao.gateway.server;

import com.zrzhao.gateway.filter.chain.HttpRequestFilterChain;
import com.zrzhao.gateway.filter.chain.HttpResponseFilterChain;
import com.zrzhao.gateway.inbound.HttpInboundHandler;
import com.zrzhao.gateway.outbound.HttpOutboundHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 客户端请求处理
 *
 * @author zrzhao
 */
public class HttpBoundInitializer extends ChannelInitializer<SocketChannel> {

	private final HttpRequestFilterChain httpRequestFilterChain;
	private final HttpResponseFilterChain httpResponseFilterChain;

	public HttpBoundInitializer(HttpRequestFilterChain httpRequestFilterChain, HttpResponseFilterChain httpResponseFilterChain) {
		this.httpRequestFilterChain = httpRequestFilterChain;
		this.httpResponseFilterChain = httpResponseFilterChain;
	}

	@Override
	public void initChannel(SocketChannel ch) {
		ChannelPipeline p = ch.pipeline();
		p.addLast(new HttpServerCodec());
		p.addLast(new HttpObjectAggregator(1024 * 1024));
		p.addLast(new HttpInboundHandler(httpRequestFilterChain));
		p.addLast(new HttpOutboundHandler(httpResponseFilterChain));
	}
}
