package com.zrzhao.concurrent.solution;

import com.zrzhao.concurrent.utils.FixedThreadPool;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用 Condition
 *
 * @author zrzhao
 */
public class Solution8 {

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        final int[] resultHolder = new int[1];

        ReentrantLock reentrantLock = new ReentrantLock();
        Condition condition = reentrantLock.newCondition();

        try {
            FixedThreadPool.newThreadPoolExecutor().execute(() -> {
                reentrantLock.lock();
                resultHolder[0] = sum();
                condition.signal();
                reentrantLock.unlock();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        reentrantLock.lock();
        condition.await();

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
