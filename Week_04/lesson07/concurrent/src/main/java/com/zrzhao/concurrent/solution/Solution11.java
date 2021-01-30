package com.zrzhao.concurrent.solution;

import com.zrzhao.concurrent.utils.FixedThreadPool;

import java.util.concurrent.SynchronousQueue;

/**
 * 使用 SynchronousQueue
 *
 * @author zrzhao
 */
public class Solution11 {

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        SynchronousQueue<Integer> synchronousQueue = new SynchronousQueue<>();

        FixedThreadPool.newThreadPoolExecutor().execute(() -> synchronousQueue.offer(sum()));

        // 这是得到的返回值
        int result = synchronousQueue.take();

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
