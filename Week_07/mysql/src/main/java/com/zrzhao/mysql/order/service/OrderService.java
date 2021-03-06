package com.zrzhao.mysql.order.service;

import com.zrzhao.mysql.batchinsert.bo.Order;

/**
 * @author zrzhao
 * @date 2021/3/6
 */
public interface OrderService {

    Order readOrderByOrderNum(String orderNum);

    Order getOrderByOrderNum(String orderNum);

}
