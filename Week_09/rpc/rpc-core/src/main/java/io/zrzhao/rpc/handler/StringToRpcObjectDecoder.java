package io.zrzhao.rpc.handler;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.zrzhao.rpc.pojo.NetMessage;

import java.util.List;

/**
 * 解码
 *
 * @author zrzhao
 * @date 2021/3/27
 */
@ChannelHandler.Sharable
public class StringToRpcObjectDecoder extends MessageToMessageDecoder<String> {

    @Override
    protected void decode(ChannelHandlerContext ctx, String in, List<Object> out) throws Exception {
        NetMessage netMessage = JSONObject.parseObject(in, NetMessage.class);
        Class<?> aClass = this.getClass().getClassLoader().loadClass(netMessage.getClassName());
        out.add(JSONObject.parseObject(netMessage.getContent(), aClass));
    }

}
