package io.zrzhao.rpc.handler;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.zrzhao.rpc.pojo.NetMessage;

import java.nio.charset.StandardCharsets;

/**
 * @author zrzhao
 * @date 2021/5/10
 */
public class NetMessageToByteEncoder extends MessageToByteEncoder<NetMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, NetMessage netMessage, ByteBuf out) throws Exception {
        out.writeCharSequence(JSONObject.toJSONString(netMessage), StandardCharsets.UTF_8);
    }
}
