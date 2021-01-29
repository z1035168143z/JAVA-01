package com.zrzhao.concurrent.soulution5;

import com.zrzhao.concurrent.utils.FixedThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 使用 Semaphore
 *
 * @author zrzhao
 */
public class Solution {

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        int[] resultHolder = new int[1];

        Object lockObj = new Object();

        ExecutorService executorService = FixedThreadPool.newThreadPoolExecutor();

        executorService.execute(() -> {
            lockObj.notifyAll();
            resultHolder[0] = sum();
            System.out.println("异步线程赋值完毕");
        });
        Thread.currentThread().wait();
        block();

        int result = resultHolder[0];

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

        // 然后退出main线程
    }

    private static synchronized void block() {
        System.out.println("阻塞");
    }
    private static synchronized int sum() {
        return recursion(36);
    }

    private static int recursion(int a) {
        if (a < 2) {
            return 1;
        }
        return recursion(a - 1) + recursion(a - 2);
    }
}
