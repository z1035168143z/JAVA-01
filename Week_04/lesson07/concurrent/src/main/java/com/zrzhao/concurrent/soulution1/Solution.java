package com.zrzhao.concurrent.soulution1;

import com.zrzhao.concurrent.utils.FixedThreadPool;

import java.util.concurrent.*;

/**
 * 判断是否 被赋值
 *
 * @author zrzhao
 */
public class Solution {

    volatile static int[] resultHolder = new int[1];

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        ExecutorService executorService = FixedThreadPool.newThreadPoolExecutor();
        executorService.submit(() -> resultHolder[0] = sum());

        // 等待线程池异步修改结果
        while (resultHolder[0] == 0) {
            Thread.yield();
            System.out.println("堂堂主线程等着异步线程执行");
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
