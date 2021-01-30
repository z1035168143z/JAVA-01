package com.zrzhao.concurrent.solution;

import java.util.concurrent.locks.LockSupport;

/**
 * 使用 LockSupport.park
 *
 * @author zrzhao
 */
public class Solution7 {

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        final int[] resultHolder = new int[1];

        Thread mainThread = Thread.currentThread();

        Thread thread = new Thread(() -> {
            resultHolder[0] = sum();
            LockSupport.unpark(mainThread);
        });

        thread.start();

        LockSupport.park();

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
