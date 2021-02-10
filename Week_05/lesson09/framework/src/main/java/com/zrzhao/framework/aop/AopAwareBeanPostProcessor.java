package com.zrzhao.framework.aop;

import com.zrzhao.framework.beans.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.BeansException;

/**
 * @author zrzhao
 * @date 2021/2/6
 */
public class AopAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, Object target) throws BeansException {
        return ProxyFactory.getProxy(target).getProxyResult(this.getClass().getClassLoader());
    }

}
