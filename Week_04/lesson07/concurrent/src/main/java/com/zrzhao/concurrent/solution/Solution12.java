package com.zrzhao.concurrent.solution;

import com.zrzhao.concurrent.utils.FixedThreadPool;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 使用 AtomicReference
 *
 * @author zrzhao
 */
public class Solution12 {

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        AtomicReference<Integer> atomicReference = new AtomicReference<>();

        FixedThreadPool.newThreadPoolExecutor().execute(() -> atomicReference.set(sum()));

        // 这是得到的返回值
        while (atomicReference.compareAndSet(null, null)) {
            System.out.println("等等异步线程修改结果");
        }

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + atomicReference.get());

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
