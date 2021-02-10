package com.zrzhao.framework.beans;

/**
 * @author zrzhao
 * @date 2021/2/6
 */
public enum StaticApplicationContent {

    INSTANCE,
    ;

    private BeanFactory beanFactory;

    StaticApplicationContent() {
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}
