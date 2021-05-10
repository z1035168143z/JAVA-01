package io.zrzhao.rpcregister.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.zrzhao.rpcregister.pojo.RpcContext;
import io.zrzhao.rpcregister.pojo.RpcRequest;

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

        ctx.writeAndFlush(new RpcMethodInvoker().process(rpcContext, (RpcRequest) msg));
    }
}
