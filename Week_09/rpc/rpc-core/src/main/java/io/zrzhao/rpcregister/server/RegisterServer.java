package io.zrzhao.rpcregister.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.zrzhao.rpcregister.handler.ProviderRegisterHandler;

/**
 * 接收 provider 的注册
 *
 * @author zrzhao
 * @date 2021/2/10
 */
public class RegisterServer {

    private int port;

    public RegisterServer(int port) {
        this.port = port;
    }

    public void start() throws InterruptedException {
        ProviderRegisterHandler registerHandler = new ProviderRegisterHandler();

        EventLoopGroup eventLoopGroup = new NioEventLoopGroup(2);

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(eventLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel c) throws Exception {
                            c.pipeline().addLast(registerHandler);
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            eventLoopGroup.shutdownGracefully().sync();
        }


    }


}
