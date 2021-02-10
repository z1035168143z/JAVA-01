package com.zrzhao.framework.beans;

import org.springframework.beans.BeansException;
import org.springframework.lang.Nullable;

/**
 * @author zrzhao
 * @date 2021/2/6
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    @Nullable
    default Object postProcessBeforeInstantiation(Class<?> beanClass, Object target) throws BeansException {
        return null;
    }

}
