package com.zrzhao.framework.aop;

/**
 * @author zrzhao
 * @date 2021/2/6
 */
public class CglibAopProxy implements AopProxy {


    private final Object target;

    public CglibAopProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object getProxyResult(ClassLoader classLoader) {
        // TODO
        return target;
    }
}
