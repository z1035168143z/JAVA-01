package io.zrzhao.shardingproxydemo.order.mapper;

import io.zrzhao.shardingproxydemo.order.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author zrzhao
 * @date 2021/5/4
 */
@Mapper
public interface IOrder {

    /**
     * create
     *
     * @param order
     * @return
     */
    int save(Order order);

    /**
     * read
     *
     * @param orderNo
     * @return
     */
    Order findByOrderNo(@Param("orderNo") String orderNo);

    /**
     * count
     *
     * @return
     */
    int count();

    /**
     * update
     *
     * @param order
     * @return
     */
    int update(Order order);

    /**
     * delete
     *
     * @param orderNo
     * @return
     */
    int deleteByOrderNo(@Param("orderNo") String orderNo);

}
