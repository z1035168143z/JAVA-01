package io.zrzhao.rpcregister.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.zrzhao.rpcregister.pojo.RpcResult;
import io.zrzhao.rpcregister.utils.JSONObject;

import java.nio.charset.StandardCharsets;

/**
 * @author zrzhao
 * @date 2021/5/10
 */
public class RpcResultToByteEncoder extends MessageToByteEncoder<RpcResult> {

    @Override
    protected void encode(ChannelHandlerContext ctx, RpcResult rpcResult, ByteBuf out) throws Exception {
        out.writeCharSequence(JSONObject.toJsonString(rpcResult), StandardCharsets.UTF_8);
    }
}
