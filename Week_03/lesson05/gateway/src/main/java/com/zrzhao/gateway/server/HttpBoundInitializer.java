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

	private final ChannelHandler[] channelHandlers;

	public HttpBoundInitializer(ChannelHandler... channelHandlers) {
		this.channelHandlers = channelHandlers;
	}

	@Override
	public void initChannel(SocketChannel ch) {
		ChannelPipeline p = ch.pipeline();
		p.addLast(new HttpServerCodec());
		p.addLast(new HttpObjectAggregator(1024 * 1024));
		for (ChannelHandler channelHandler : this.channelHandlers) {
			p.addLast(channelHandler);
		}
	}
}
