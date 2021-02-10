package com.zrzhao.demo.singleton;

/**
 * 枚举式单例
 *
 * 最安全
 *
 * @author zrzhao
 * @date 2021/2/9
 */
public enum EnumSingleton {

    INSTANCE
    ;

    public void doSomeThing() {
        System.out.println(this);
    }

}
