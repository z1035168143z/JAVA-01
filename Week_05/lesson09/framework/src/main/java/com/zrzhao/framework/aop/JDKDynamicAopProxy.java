package com.zrzhao.framework.aop;

import com.zrzhao.framework.aop.pointcat.GoodEat;
import com.zrzhao.framework.beans.StaticApplicationContent;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author zrzhao
 * @date 2021/2/6
 */
public class JDKDynamicAopProxy implements InvocationHandler, AopProxy {

    private final Object target;

    public JDKDynamicAopProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        AopMethodInvocation aopMethodInvocation = new AopMethodInvocation(target, method, args);

        GoodEat goodEat = StaticApplicationContent.INSTANCE.getBeanFactory().getBean("goodEat", GoodEat.class);

        aopMethodInvocation.addPreInvokeMethod(new EnhancementMethod(goodEat, GoodEat.class.getMethod("good")));

        return aopMethodInvocation.proceed();
    }

    @Override
    public Object getProxyResult(ClassLoader classLoader) {
        return Proxy.newProxyInstance(classLoader, this.target.getClass().getInterfaces(), this);
    }
}
