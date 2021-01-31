package com.zrzhao.concurrent.solution;

/**
 * 使用 Thread.activeCount
 *
 * @author zrzhao
 */
public class Solution6 {

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        final int[] resultHolder = new int[1];

        Thread thread = new Thread(() -> resultHolder[0] = sum());

        thread.start();

        while (Thread.activeCount() > 2) {
            System.out.println("好像还有线程没关闭，等等它");
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
