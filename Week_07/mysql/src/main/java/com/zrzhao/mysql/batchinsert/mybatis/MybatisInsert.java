package com.zrzhao.mysql.batchinsert.mybatis;

import com.zrzhao.mysql.batchinsert.bo.Order;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 测试使用mybatis批量插入 1百万数据
 *
 * 本机数据库
 * <pre>
 *  批量提交条数 | 关闭日志执行时间(ms)
 *  1000       | 61149
 *  5000       | 43229
 *  10000      | 38048
 *  50000      | 40165
 *  100000     | 44740
 * </pre>
 * @author zrzhao
 * @date 2021/3/6
 */
public class MybatisInsert {

    public static void main(String[] args) throws Exception {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();

        InputStream resourceAsStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(resourceAsStream);

        SqlSession sqlSession = sqlSessionFactory.openSession();
        IOrder orderDao = sqlSession.getMapper(IOrder.class);

        long begin = System.currentTimeMillis();

        int batchSize = 50000;
        List<Order> orderList = new ArrayList<>(batchSize);
        int insertTimes = 1000000;
        for (int i = 0; i < insertTimes; i++) {
            orderList.add(new Order(String.valueOf(i), i, i, i, new BigDecimal(i), String.valueOf(i), i));

            if (orderList.size() == batchSize) {
                orderDao.saveOrder(orderList);
                sqlSession.commit();
                orderList.clear();
                System.out.println(i);
            }
        }

        long end = System.currentTimeMillis();
        System.out.println(end - begin);
    }

}
