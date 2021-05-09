package io.zrzhao.rpcregister.bootstrap;

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
import io.zrzhao.rpcregister.handler.EchoClientHandler;
import lombok.AllArgsConstructor;

import java.net.InetSocketAddress;
import java.util.Scanner;

/**
 * @author zrzhao
 * @date 2021/2/11
 */
@AllArgsConstructor
public class ConsumerBootStrap {

    private final String host;
    private final int port;

    public void start() throws InterruptedException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host, port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new EchoClientHandler());
                        }
                    });
            ChannelFuture future = bootstrap.connect().sync();
            Channel channel = future.channel();

            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                channel.writeAndFlush(Unpooled.copiedBuffer(line + "\r\n", CharsetUtil.UTF_8));
            }

            channel.closeFuture().sync();
        } finally {
            eventLoopGroup.shutdownGracefully().sync();
        }

    }

}
