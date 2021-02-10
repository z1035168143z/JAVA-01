package com.zrzhao.framework.beans;

/**
 * bean definition
 *
 * @author zrzhao
 * @date 2021/2/6
 */
public class BeanDefinition {

    /** customized bean name  **/
    private String beanName;
    /** bean actual class **/
    private Class<?> beanClass;
    /** bean init parameter type **/
    private Class<?>[] parameterTypes;
    /** bean init parameter **/
    private Object[] parameters;

    public BeanDefinition() {
    }

    public BeanDefinition(String beanName, Class<?> beanClass) {
        this.beanName = beanName;
        this.beanClass = beanClass;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }
}
