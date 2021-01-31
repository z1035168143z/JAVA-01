package com.zrzhao.concurrent.solution;

import com.zrzhao.concurrent.utils.FixedThreadPool;

import java.util.concurrent.FutureTask;

/**
 * 使用 FutureTask
 *
 * @author zrzhao
 */
public class Solution9 {

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        FutureTask<Integer> futureTask = new FutureTask<>(Solution9::sum);
        FixedThreadPool.newThreadPoolExecutor().submit(futureTask);

        // 这是得到的返回值
        int result = futureTask.get();

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
