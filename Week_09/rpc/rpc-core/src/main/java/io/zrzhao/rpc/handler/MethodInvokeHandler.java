package io.zrzhao.rpc.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.zrzhao.rpc.pojo.RpcContext;
import io.zrzhao.rpc.pojo.RpcRequest;

import java.lang.reflect.InvocationTargetException;

/**
 * @author zrzhao
 * @date 2021/5/10
 */
@ChannelHandler.Sharable
public class MethodInvokeHandler extends ChannelInboundHandlerAdapter {

    private RpcContext rpcContext;

    public MethodInvokeHandler(RpcContext rpcContext) {
        this.rpcContext = rpcContext;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof RpcRequest)) {
            super.channelRead(ctx, msg);
            return;
        }

        new Thread(() ->{
            try {
                ctx.writeAndFlush(new RpcMethodInvoker().process(rpcContext, (RpcRequest) msg));
            } catch (InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
