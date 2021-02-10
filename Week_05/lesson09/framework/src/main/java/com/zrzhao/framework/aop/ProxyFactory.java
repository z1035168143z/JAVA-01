package com.zrzhao.framework.aop;

/**
 * @author zrzhao
 * @date 2021/2/6
 */
public class ProxyFactory {

    public static AopProxy getProxy(Object target) {
        Class<?>[] interfaces = target.getClass().getInterfaces();
        if (interfaces.length == 0) {
            return new CglibAopProxy(target);
        }
        return new JDKDynamicAopProxy(target);
    }


}
