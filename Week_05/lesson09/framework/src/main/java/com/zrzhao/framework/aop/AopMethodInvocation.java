package com.zrzhao.framework.aop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AopMethodInvocation {

    private final Object target;

    private final Method method;

    private final Object[] arguments;

    /** invoke before proxy method **/
    private final List<EnhancementMethod> preInvokeMethod = new ArrayList<>();
    /** invoke after proxy method **/
    private final List<EnhancementMethod> postInvokeMethod = new ArrayList<>();

    protected AopMethodInvocation(Object target, Method method, Object[] arguments) {
        this.target = target;
        this.method = method;
        this.arguments = arguments;
    }

    public Object proceed() throws Throwable {
        for (EnhancementMethod enhancementMethod : preInvokeMethod) {
            enhancementMethod.invoke(this.arguments);
        }
        Object result = invokeJoinpoint();

        for (EnhancementMethod enhancementMethod : postInvokeMethod) {
            enhancementMethod.invoke(this.arguments);
        }
        return result;
    }

    protected Object invokeJoinpoint() throws Throwable {
        return ProxyUtils.invokeJoinpointUsingReflection(this.target, this.method, this.arguments);
    }

    public AopMethodInvocation addPreInvokeMethod(EnhancementMethod enhancementMethod) {
        this.preInvokeMethod.add(enhancementMethod);
        return this;
    }

    public AopMethodInvocation addPostInvokeMethod(EnhancementMethod enhancementMethod) {
        this.postInvokeMethod.add(enhancementMethod);
        return this;
    }

}
