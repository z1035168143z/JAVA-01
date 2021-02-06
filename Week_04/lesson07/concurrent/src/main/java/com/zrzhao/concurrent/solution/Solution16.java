package com.zrzhao.concurrent.solution;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * 使用 ForkJoinPool()和 ForkJoinTask()
 *
 * @author zrzhao
 */
public class Solution16 {

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        ForkJoinPool forkJoinPool = new ForkJoinPool();

        ForkJoinTask<Integer> forkJoinTask = new RecursiveTask<Integer>() {
            @Override
            protected Integer compute() {
                return sum();
            }
        };

        ForkJoinTask<Integer> submit = forkJoinPool.submit(forkJoinTask);

        int result = submit.get();

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
