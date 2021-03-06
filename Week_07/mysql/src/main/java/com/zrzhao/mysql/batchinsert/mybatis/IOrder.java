package com.zrzhao.mysql.batchinsert.mybatis;

import com.zrzhao.mysql.batchinsert.bo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zrzhao
 * @date 2021/3/6
 */
public interface IOrder {

    int saveOrder(@Param("orders") List<Order> orders);

}
