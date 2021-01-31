package com.zrzhao.concurrent.solution;

import com.zrzhao.concurrent.utils.FixedThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;

/**
 * 使用 Semaphore
 *
 * @author zrzhao
 */
public class Solution4 {

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        ExecutorService executorService = FixedThreadPool.newThreadPoolExecutor();

        final int[] resultHolder = new int[1];

        Semaphore semaphore = new Semaphore(1);

        semaphore.acquire();
        executorService.execute(() -> {
            try {
                resultHolder[0] = sum();
            } finally {
                semaphore.release();
            }
        });

        while (!semaphore.tryAcquire()) {
            System.out.println("没人干主线程的活");
        }

        // 这是得到的返回值
        int result = resultHolder[0];

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

        // 然后退出main线程
    }

    private static int sum() {
        return recursion(36);
    }

    private static int recursion(int a) {
        if (a < 2) {
            return 1;
        }
        return recursion(a - 1) + recursion(a - 2);
    }
}
