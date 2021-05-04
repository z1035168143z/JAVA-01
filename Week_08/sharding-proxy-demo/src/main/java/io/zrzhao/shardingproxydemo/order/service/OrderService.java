package io.zrzhao.shardingproxydemo.order.service;

/**
 * @author zrzhao
 * @date 2021/5/4
 */
public interface OrderService {

    void batchInsert(boolean commit);

    int countOrder();

}
