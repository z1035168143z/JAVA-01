package com.zrzhao.concurrent.solution;

import com.zrzhao.concurrent.utils.FixedThreadPool;

import java.util.concurrent.ExecutorService;

/**
 * 使用 wait notify
 *
 * @author zrzhao
 */
public class Solution14 {

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        ExecutorService executorService = FixedThreadPool.newThreadPoolExecutor();

        final int[] resultHolder = new int[1];

        Object lockObj = new Object();

        executorService.execute(() -> {
            synchronized (lockObj) {
                resultHolder[0] = sum();
                lockObj.notify();
            }
        });

        int result = 0;

        synchronized (lockObj) {
            lockObj.wait();
            // 这是得到的返回值
            result = resultHolder[0];
        }

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
