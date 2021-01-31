package com.zrzhao.concurrent.nasolution;

import com.zrzhao.concurrent.utils.FixedThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 使用 synchronized 和 sleep...
 *
 * @author zrzhao
 */
public class Solution1 {

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        int[] resultHolder = new int[1];

        ExecutorService executorService = FixedThreadPool.newThreadPoolExecutor();

        executorService.execute(() -> {
            resultHolder[0] = sum();
            System.out.println("异步线程赋值完毕");
        });

        // 异步线程根本抢不过主线程。。  sleep 1 nans 可以实现使用synchronized
        TimeUnit.NANOSECONDS.sleep(1);

        block();

        int result = resultHolder[0];

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

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
