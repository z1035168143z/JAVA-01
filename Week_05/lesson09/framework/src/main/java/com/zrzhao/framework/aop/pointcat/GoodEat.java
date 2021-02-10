package com.zrzhao.framework.aop.pointcat;

import com.zrzhao.framework.beans.annotation.Definition;

/**
 * @author zrzhao
 * @date 2021/2/6
 */
@Definition("goodEat")
public class GoodEat {

    private String name;
    private String age;

    public GoodEat(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public void good() {
        System.out.println("name : " + name + ",age : " + age + ".have a skill for eat.");
    }

}
