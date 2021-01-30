package com.zrzhao.concurrent.solution;

import com.zrzhao.concurrent.utils.FixedThreadPool;

import java.io.*;

/**
 * 使用 swapFile
 *
 * @author zrzhao
 */
public class Solution5 {

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        String templatePath = Solution5.class.getResource("/").getPath();
        if (templatePath.contains(":")) {
            templatePath = templatePath.substring(1);
        }

        File file = new File(templatePath + "swapFile.dat");

        BufferedReader reader = new BufferedReader(new FileReader(file));

        FixedThreadPool.newThreadPoolExecutor().execute(() -> {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(String.valueOf(sum()));
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        String lineStr;
        // 这是得到的返回值
        while ((lineStr = reader.readLine()) == null) {
            System.out.println("等等异步线程写进去");
        }

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + lineStr);

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
