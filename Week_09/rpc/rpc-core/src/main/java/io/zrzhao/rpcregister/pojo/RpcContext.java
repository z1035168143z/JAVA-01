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

    /**
     *  class : [method,method,method]
     */
    private final MultiValueMap<String, ProviderMethod> providerMethodStore = new LinkedMultiValueMap<>();


}
