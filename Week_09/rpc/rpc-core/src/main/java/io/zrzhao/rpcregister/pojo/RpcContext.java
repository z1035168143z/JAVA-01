package io.zrzhao.rpcregister.pojo;

import lombok.Getter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * @author zrzhao
 * @date 2021/5/9
 */
@Getter
public class RpcContext {

    private RpcContext() {

    }

    private static final RpcContext INSTANCE = new RpcContext();

    public static RpcContext getContext() {
        return INSTANCE;
    }

    /**
     * class : [method,method,method]
     */
    private final MultiValueMap<String, ProviderMethod> providerMethodStore = new LinkedMultiValueMap<>();


}
