package com.zrzhao.demo.singleton;

/**
 * 静态内部类
 *
 * @author zrzhao
 * @date 2021/2/9
 */
public class InnerStaticClassSingleton {

    private InnerStaticClassSingleton() {

    }

    static class singletonHolder{
        private static final InnerStaticClassSingleton instance = new InnerStaticClassSingleton();
    }

    public static InnerStaticClassSingleton getSingleton() {
        return singletonHolder.instance;
    }

    public void doSomeThing() {
        System.out.println("inner static class: " + this);
    }

}
