package com.zrzhao.human.impl;

import com.zrzhao.framework.beans.annotation.Definition;
import com.zrzhao.human.Sleepable;

/**
 * @author zrzhao
 * @date 2021/2/9
 */
@Definition("daySleep")
public class DayDaySleep implements Sleepable {

    @Override
    public void sleep() {
        System.out.println("today is a good day to sleep");
    }
}
