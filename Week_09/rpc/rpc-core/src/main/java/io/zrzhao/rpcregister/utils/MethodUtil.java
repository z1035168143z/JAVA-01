package io.zrzhao.rpcregister.utils;

import io.zrzhao.rpcregister.pojo.RpcRequest;
import org.springframework.util.DigestUtils;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author zrzhao
 * @date 2021/5/9
 */
public class MethodUtil {

    public static String createMethodSign(Method method) {
        if (method == null) {
            return "";
        }
        StringBuilder methodSign = new StringBuilder("method sign:");
        methodSign.append(method.getName()).append(":");
        Arrays.stream(method.getParameterTypes()).forEach(clazz -> methodSign.append(clazz.getName()).append(","));
        return DigestUtils.md5DigestAsHex(methodSign.toString().getBytes());
    }

    public static String createMethodSign(RpcRequest request) {
        if (request == null) {
            return "";
        }
        StringBuilder methodSign = new StringBuilder("method sign:");
        methodSign.append(request.getMethodName()).append(":");
        Arrays.stream(request.getArgs()).forEach(clazz -> methodSign.append(clazz.getClass().getName()).append(","));
        return DigestUtils.md5DigestAsHex(methodSign.toString().getBytes());
    }

}
