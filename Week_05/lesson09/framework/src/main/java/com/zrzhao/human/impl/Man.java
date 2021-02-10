package com.zrzhao.human.impl;

import com.zrzhao.framework.beans.annotation.Definition;
import com.zrzhao.framework.beans.annotation.Inject;
import com.zrzhao.human.Eatable;
import com.zrzhao.human.Sleepable;

/**
 * simple man
 *
 * @author zrzhao
 * @date 2021/2/6
 */
@Definition("man")
public class Man implements Eatable {

    @Inject("daySleep")
    private Sleepable sleepable;

    @Override
    public void eat() {
        System.out.println("Today is also a day for eat.");

        sleepable.sleep();
    }
}
