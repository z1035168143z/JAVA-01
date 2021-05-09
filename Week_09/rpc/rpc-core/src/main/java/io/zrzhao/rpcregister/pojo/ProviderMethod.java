package io.zrzhao.rpcregister.pojo;

import io.zrzhao.rpcregister.utils.MethodUtil;
import lombok.Data;

import java.lang.reflect.Method;

/**
 * provider 注册信息
 *
 * @author zrzhao
 * @date 2021/3/27
 */
@Data
public class ProviderMethod {

    /**
     * 方法名称
     */
    private Method method;

    /**
     * 方法签名
     */
    private String methodSign;

    /**
     * 方法实现
     */
    private Object methodImpl;

    public static ProviderMethod create(Method method, Object methodImpl) {
        ProviderMethod providerMethod = new ProviderMethod();

        providerMethod.methodSign = MethodUtil.createMethodSign(method);
        providerMethod.method = method;
        providerMethod.methodImpl = methodImpl;

        return providerMethod;
    }

}
