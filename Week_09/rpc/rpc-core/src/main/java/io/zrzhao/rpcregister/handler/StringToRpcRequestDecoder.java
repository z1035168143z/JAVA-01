package io.zrzhao.rpcregister.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.zrzhao.rpcregister.pojo.NetMessage;
import io.zrzhao.rpcregister.utils.JSONObject;

import java.util.List;

/**
 * 解码
 *
 * @author zrzhao
 * @date 2021/3/27
 */
public class StringToRpcRequestDecoder extends MessageToMessageDecoder<String> {

    @Override
    protected void decode(ChannelHandlerContext ctx, String in, List<Object> out) throws Exception {
        NetMessage netMessage = JSONObject.parseObject(in, NetMessage.class);
        Class<?> aClass = this.getClass().getClassLoader().loadClass(netMessage.getClassName());
        out.add(JSONObject.parseObject(netMessage.getContent(), aClass));
    }


}
