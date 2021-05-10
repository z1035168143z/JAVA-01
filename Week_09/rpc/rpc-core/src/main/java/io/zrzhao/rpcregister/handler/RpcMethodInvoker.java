package io.zrzhao.rpcregister.handler;

import io.zrzhao.rpcregister.pojo.ProviderMethod;
import io.zrzhao.rpcregister.pojo.RpcContext;
import io.zrzhao.rpcregister.pojo.RpcRequest;
import io.zrzhao.rpcregister.pojo.RpcResult;
import io.zrzhao.rpcregister.utils.MethodUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

/**
 * @author zrzhao
 * @date 2021/5/10
 */
@Slf4j
public class RpcMethodInvoker {

    public RpcResult process(RpcContext rpcContext, RpcRequest request) throws InvocationTargetException, IllegalAccessException {
        MultiValueMap<String, ProviderMethod> providerMethodStore = rpcContext.getProviderMethodStore();
        List<ProviderMethod> providerMethods = providerMethodStore.get(request.getClazzName());
        if (providerMethods.isEmpty()) {
            log.error("no provider for method invoke");
            return null;
        }
        String methodSign = MethodUtil.createMethodSign(request);

        Optional<ProviderMethod> methodOptional = providerMethods.stream().filter(providerMethod -> providerMethod.getMethodSign().equals(methodSign)).findFirst();
        ProviderMethod providerMethod = methodOptional.orElse(null);
        if (providerMethod == null) {
            log.error("no provider for method invoke");
            return null;
        }

        return new RpcResult(0, providerMethod.getMethod().invoke(providerMethod.getMethodImpl(), request.getArgs()));
    }

}
