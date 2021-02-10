package com.zrzhao.framework.aop;

/**
 * @author zrzhao
 * @date 2021/2/6
 */
public interface AopProxy {

    Object getProxyResult(ClassLoader classLoader);

}
