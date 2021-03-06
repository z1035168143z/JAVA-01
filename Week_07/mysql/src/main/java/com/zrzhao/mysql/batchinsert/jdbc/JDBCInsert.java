package com.zrzhao.mysql.batchinsert.jdbc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.concurrent.CountDownLatch;

/**
 *
 * <pre>
 *  *  批量提交条数 | 关闭日志执行时间(ms)
 *  *  1000       | 47524
 *  *  5000       | 29264
 *  *  10000      | 26147
 *  *  50000      | 23937
 *  *  100000      | 22145
 *
 *
 *  3个线程  ： 16555
 *  * </pre>
 *
 * @author zrzhao
 * @date 2021/3/6
 */
public class JDBCInsert {

    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://127.0.0.1:3306/test?rewriteBatchedStatements=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&serverTimezone=GMT%2B8";
        String sql = "insert into t_order (`order_no`, `owner_id`, `product_id`, `receipt_address_id`, `order_amount`, `status`, `order_snapshot`)" +
                "        values (?,?,?,?,?,?,?)";
        long begin = System.currentTimeMillis();

        CountDownLatch countDownLatch = new CountDownLatch(4);
        final int batchSize = 100000;
        int insertTimes = 1000000;
        new Thread(() -> {
            try {
                Connection connection = DriverManager.getConnection(url, "root", null);
                connection.setAutoCommit(false);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                for (int i = 1; i <= 250000; i++) {
                    preparedStatement.setString(1, String.valueOf(i));
                    preparedStatement.setInt(2, i);
                    preparedStatement.setInt(3, i);
                    preparedStatement.setInt(4, i);
                    preparedStatement.setBigDecimal(5, new BigDecimal(i));
                    preparedStatement.setString(6, String.valueOf(i));
                    preparedStatement.setInt(7, i);
                    preparedStatement.addBatch();
                    if (i % batchSize == 0) {
                        preparedStatement.executeBatch();
                        preparedStatement.clearBatch();
                        connection.commit();
                    }
                }
                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            try {
                Connection connection = DriverManager.getConnection(url, "root", null);
                connection.setAutoCommit(false);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);

                for (int i = 250001; i <= 500000; i++) {
                    preparedStatement.setString(1, String.valueOf(i));
                    preparedStatement.setInt(2, i);
                    preparedStatement.setInt(3, i);
                    preparedStatement.setInt(4, i);
                    preparedStatement.setBigDecimal(5, new BigDecimal(i));
                    preparedStatement.setString(6, String.valueOf(i));
                    preparedStatement.setInt(7, i);
                    preparedStatement.addBatch();
                    if (i % batchSize == 0) {
                        preparedStatement.executeBatch();
                        preparedStatement.clearBatch();
                        connection.commit();
                    }
                }
                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            try {
                Connection connection = DriverManager.getConnection(url, "root", null);
                connection.setAutoCommit(false);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);

                for (int i = 500001; i <= 750000; i++) {
                    preparedStatement.setString(1, String.valueOf(i));
                    preparedStatement.setInt(2, i);
                    preparedStatement.setInt(3, i);
                    preparedStatement.setInt(4, i);
                    preparedStatement.setBigDecimal(5, new BigDecimal(i));
                    preparedStatement.setString(6, String.valueOf(i));
                    preparedStatement.setInt(7, i);
                    preparedStatement.addBatch();
                    if (i % batchSize == 0) {
                        preparedStatement.executeBatch();
                        preparedStatement.clearBatch();
                        connection.commit();
                    }
                }
                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()->{
            try {
                Connection connection = DriverManager.getConnection(url, "root", null);
                connection.setAutoCommit(false);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);

                for (int i = 750001; i <= 1000000; i++) {
                    preparedStatement.setString(1, String.valueOf(i));
                    preparedStatement.setInt(2, i);
                    preparedStatement.setInt(3, i);
                    preparedStatement.setInt(4, i);
                    preparedStatement.setBigDecimal(5, new BigDecimal(i));
                    preparedStatement.setString(6, String.valueOf(i));
                    preparedStatement.setInt(7, i);
                    preparedStatement.addBatch();
                    if (i % batchSize == 0) {
                        preparedStatement.executeBatch();
                        preparedStatement.clearBatch();
                        connection.commit();
                    }
                }
                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        countDownLatch.await();
        long end = System.currentTimeMillis();
        System.out.println(end - begin);

    }


}
