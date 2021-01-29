package com.zrzhao.concurrent.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zrzhao
 * @date 2021/1/29
 */
public class FixedThreadPool {

    public static ExecutorService newThreadPoolExecutor() {
        return new ThreadPoolExecutor(1, 1,
                1L, TimeUnit.NANOSECONDS,
                new LinkedBlockingQueue<>(), r -> {
            Thread thread = new Thread(r);
            thread.setName("异步线程池线程");
            thread.setDaemon(true);
            return thread;
        });
    }

}
