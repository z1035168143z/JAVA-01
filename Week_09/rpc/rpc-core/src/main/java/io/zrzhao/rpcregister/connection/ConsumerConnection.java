package io.zrzhao.rpcregister.connection;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author zrzhao
 * @date 2021/5/10
 */
public class ConsumerConnection {

    public Map.Entry<String, Integer> getProviderHost() {
        Map<String, Integer> providerHostMap = new HashMap<>(16);

        providerHostMap.put("127.0.0.1", 22317);

        return providerHostMap.entrySet().iterator().next();
    }


    public Object send(Method method, Object[] args) throws Exception {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        Map.Entry<String, Integer> providerHost = getProviderHost();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(providerHost.getKey(), providerHost.getValue()))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new EchoClientHandler());
                        }
                    });
            ChannelFuture future = bootstrap.connect().sync();
            Channel channel = future.channel();

            ChannelFuture channelFuture = channel.writeAndFlush(Unpooled.copiedBuffer(line + "\r\n", CharsetUtil.UTF_8));

            channel.closeFuture().sync();
        } finally {
            eventLoopGroup.shutdownGracefully().sync();
        }


    }

}
