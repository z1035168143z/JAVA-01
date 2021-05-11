package io.zrzhao.rpc.handler;

import com.alibaba.fastjson.JSONObject;
import io.zrzhao.rpc.pojo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

/**
 * @author zrzhao
 * @date 2021/5/10
 */
@Slf4j
public class RpcMethodInvoker {

    public NetMessage process(RpcContext rpcContext, RpcRequest request) throws InvocationTargetException, IllegalAccessException {
        MultiValueMap<String, ProviderMethod> providerMethodStore = rpcContext.getProviderMethodStore();
        List<ProviderMethod> providerMethods = providerMethodStore.get(request.getClazzName());
        if (providerMethods.isEmpty()) {
            log.error("no provider for method invoke");
            return null;
        }

        Optional<ProviderMethod> methodOptional = providerMethods.stream().filter(providerMethod -> providerMethod.getMethodSign().equals(request.getMethodSign())).findFirst();
        ProviderMethod providerMethod = methodOptional.orElse(null);
        if (providerMethod == null) {
            log.error("no provider for method invoke");
            return null;
        }

        NetMessage netMessage = new NetMessage();

        Object[] args = JSONObject.parseArray(JSONObject.toJSONString(request.getArgs()), providerMethod.getMethod().getGenericParameterTypes()).toArray();

        netMessage.setContent(JSONObject.toJSONString(new RpcResult(0, providerMethod.getMethod().invoke(providerMethod.getMethodImpl(), args), request.getRequestId())));
        netMessage.setClassName(RpcResult.class.getName());

        return netMessage;
    }

}
