package com.zrzhao.demo.singleton;

/**
 * 懒加载
 *
 * jvm 创建对象分三步
 * 1. 申请内存
 * 2. 引用赋值
 * 3. 创建对象
 * 在极端情况下 singleton != null 为 true 但是实际上对象还没有创建完成
 *
 * @author zrzhao
 * @date 2021/2/9
 */
public class LazyInitSingleton {

    private LazyInitSingleton() {

    }

    private static volatile LazyInitSingleton singleton;

    public static LazyInitSingleton getSingleton() {
        if (singleton != null) {
            return singleton;
        }
        synchronized (LazyInitSingleton.class) {
            if (singleton != null) {
                return singleton;
            }
            singleton = new LazyInitSingleton();
        }
        return singleton;
    }

    public void doSomeThing() {
        System.out.println("lazy init: " + this);
    }

}
