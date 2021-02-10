package com.zrzhao.framework.beans;

/**
 * produce beans
 *
 * @author zrzhao
 * @date 2021-02-06
 */
public interface BeanFactory {

    /** register beans **/
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    /** get bean **/
    <T> T getBean(String name, Class<T> requiredType);

}
