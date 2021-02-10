package com.zrzhao.framework.beans;

import org.springframework.beans.BeansException;

/**
 * Bean Post Processor
 *
 * @author zrzhao
 * @date 2021/2/6
 */
public interface BeanPostProcessor {

    /** Apply this BeanPostProcessor to the given new bean instance before any bean initialization callbacks **/
    default Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

}
