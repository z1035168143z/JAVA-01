package com.zrzhao.concurrent.solution;

import com.zrzhao.concurrent.utils.FixedThreadPool;

import java.util.concurrent.*;

/**
 * 使用 Phaser
 *
 * @author zrzhao
 */
public class Solution17 {

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        Phaser phaser = new Phaser();

        ExecutorService executorService = FixedThreadPool.newThreadPoolExecutor();

        final int[] resultHolder = new int[1];

        phaser.register();
        executorService.execute(() -> {
            resultHolder[0] = sum();
            phaser.arrive();
        });
        phaser.register();
        phaser.arriveAndAwaitAdvance();

        int result = resultHolder[0];

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

        // 然后退出main线程
    }

    private static int sum() {
        return recursion(42);
    }

    private static int recursion(int a) {
        if (a < 2) {
            return 1;
        }
        return recursion(a - 1) + recursion(a - 2);
    }
}
