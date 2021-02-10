package com.zrzhao.framework;

import com.zrzhao.framework.aop.AopAwareBeanPostProcessor;
import com.zrzhao.framework.beans.AbstractBeanFactory;
import com.zrzhao.framework.beans.StaticApplicationContent;
import com.zrzhao.framework.beans.XmlBeanFactory;
import com.zrzhao.human.Eatable;

public class FrameworkApplication {

    public static void main(String[] args) throws IllegalAccessException {

//        AbstractBeanFactory beanFactory = new AnnotationBeanFactory();
//        AbstractBeanFactory beanFactory = new YmlBeanFactory();
        AbstractBeanFactory beanFactory = new XmlBeanFactory();

        StaticApplicationContent.INSTANCE.setBeanFactory(beanFactory);

        beanFactory.registerBeanPostProcessor(new AopAwareBeanPostProcessor());

        beanFactory.refresh();

        Eatable eatable = beanFactory.getBean("man", Eatable.class);
        eatable.eat();
    }

}
