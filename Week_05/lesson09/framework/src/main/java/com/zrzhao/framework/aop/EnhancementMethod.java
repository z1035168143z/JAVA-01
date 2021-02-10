package com.zrzhao.framework.aop;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author zrzhao
 * @date 2021/2/6
 */
public class EnhancementMethod {

    private final Object target;
    private final Method method;

    public EnhancementMethod(Object target, Method method) {
        this.target = target;
        this.method = method;
    }

    public void invoke(Object[] arguments) throws InvocationTargetException, IllegalAccessException {
        method.invoke(target, arguments);
    }

}
