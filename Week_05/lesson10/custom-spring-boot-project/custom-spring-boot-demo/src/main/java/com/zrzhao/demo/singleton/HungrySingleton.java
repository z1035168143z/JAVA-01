package com.zrzhao.demo.singleton;

/**
 * 饿汉式
 *
 * jvm启动阶段即开始加载，影响启动速度。
 * 若程序运行过程中始终未使用该单例，会产生资源的浪费
 *
 * @author zrzhao
 * @date 2021/2/9
 */
public class HungrySingleton {

    private HungrySingleton() {

    }

    private static final HungrySingleton singleton = new HungrySingleton();

    public static HungrySingleton getSingleton() {
        return singleton;
    }

    public void doSomeThing() {
        System.out.println("hungry init：" + this);
    }
}
