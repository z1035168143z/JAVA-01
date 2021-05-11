package io.zrzhao.rpc.connection;

import com.alibaba.fastjson.JSONObject;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.zrzhao.rpc.handler.ByteToStringDecoder;
import io.zrzhao.rpc.handler.MethodResultHandler;
import io.zrzhao.rpc.handler.NetMessageToByteEncoder;
import io.zrzhao.rpc.handler.StringToRpcObjectDecoder;
import io.zrzhao.rpc.pojo.NetMessage;
import io.zrzhao.rpc.pojo.RpcContext;
import io.zrzhao.rpc.pojo.RpcRequest;
import io.zrzhao.rpc.pojo.RpcResult;
import io.zrzhao.rpc.utils.MethodUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author zrzhao
 * @date 2021/5/10
 */
@Slf4j
public class ConsumerConnection {

    public Map.Entry<String, Integer> getProviderHost() {
        Map<String, Integer> providerHostMap = new HashMap<>(16);

        // TODO
        providerHostMap.put("127.0.0.1", 22317);

        return providerHostMap.entrySet().iterator().next();
    }


    public Object send(Method method, Object[] args) throws Exception {
        final ByteToStringDecoder byteToStringDecoder = new ByteToStringDecoder();
        final StringToRpcObjectDecoder stringToRpcObjectDecoder = new StringToRpcObjectDecoder();
        final MethodResultHandler methodResultHandler = new MethodResultHandler(RpcContext.getContext());

        EventLoopGroup eventLoopGroup = new NioEventLoopGroup(2);

        Map.Entry<String, Integer> providerHost = getProviderHost();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // out
                            pipeline.addLast(new NetMessageToByteEncoder());
                            // in
                            pipeline.addLast(byteToStringDecoder);
                            pipeline.addLast(stringToRpcObjectDecoder);
                            pipeline.addLast(methodResultHandler);
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect(providerHost.getKey(), providerHost.getValue()).sync();
            Channel channel = channelFuture.channel();
            channelFuture.addListener((ChannelFutureListener) future -> {
                if (future.isDone() && future.cause() != null) {
                    log.error("{serverName:\"" + providerHost.getKey() + "\", serverPort:" + providerHost.getValue() + "}", future.cause());
                } else {
                    log.info("{msg:\"connected\", serverName:\"" + providerHost.getKey() + "\", serverPort:" + providerHost.getValue() + "}");
                }
            });


            RpcRequest rpcRequest = new RpcRequest();
            rpcRequest.setClazzName(method.getDeclaringClass().getName());
            rpcRequest.setMethodName(method.getName());
            rpcRequest.setMethodSign(MethodUtil.createMethodSign(method));
            rpcRequest.setArgs(args);

            NetMessage netMessage = new NetMessage();
            netMessage.setContent(JSONObject.toJSONString(rpcRequest));
            netMessage.setClassName(RpcRequest.class.getName());

            channel.writeAndFlush(netMessage);

            boolean isSuccess = channelFuture.awaitUninterruptibly(15, TimeUnit.SECONDS);
            if (!isSuccess) {
                channelFuture.cancel(true);
            }

            Map<Long, RpcResult> rpcResultMap = RpcContext.getContext().getRpcResultMap();
            for (int i = 0; i < 3; i++) {
                if (!rpcResultMap.containsKey(rpcRequest.getRequestId())) {
                    TimeUnit.SECONDS.sleep(1);
                    continue;
                }
                return rpcResultMap.remove(rpcRequest.getRequestId());
            }
            throw new RuntimeException("wait time out...");
        } finally {
            eventLoopGroup.shutdownGracefully().sync();
        }
    }

}
