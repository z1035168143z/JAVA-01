package io.zrzhao.rpc.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.zrzhao.rpc.pojo.RpcContext;
import io.zrzhao.rpc.pojo.RpcResult;

/**
 * @author zrzhao
 * @date 2021/5/10
 */
@ChannelHandler.Sharable
public class MethodResultHandler extends ChannelInboundHandlerAdapter {

    private final RpcContext rpcContext;

    public MethodResultHandler(RpcContext rpcContext) {
        this.rpcContext = rpcContext;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof RpcResult)) {
            super.channelRead(ctx, msg);
            return;
        }
        RpcResult rpcResult = (RpcResult) msg;
        rpcContext.getRpcResultMap().put(rpcResult.getRequestId(), rpcResult);
    }
}
