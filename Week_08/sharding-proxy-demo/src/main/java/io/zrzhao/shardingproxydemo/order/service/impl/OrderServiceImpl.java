package io.zrzhao.shardingproxydemo.order.service.impl;

import io.zrzhao.shardingproxydemo.order.entity.Order;
import io.zrzhao.shardingproxydemo.order.mapper.IOrder;
import io.zrzhao.shardingproxydemo.order.service.OrderService;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author zrzhao
 * @date 2021/5/4
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private IOrder orderDao;

    @Override
    @ShardingTransactionType(TransactionType.XA)
    public void batchInsert(boolean commit) {
        Random random = new Random(Integer.MAX_VALUE);
        for (int i = 0; i < 30; i++) {
            Order order = new Order();
            order.setOrderNo(String.valueOf(System.currentTimeMillis()));
            try {
                TimeUnit.MILLISECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            order.setOwnerId(Math.abs(random.nextInt()));
            order.setProductId(Math.abs(random.nextInt()));
            order.setReceiptAddressId(Math.abs(random.nextInt()));
            order.setOrderAmount(BigDecimal.valueOf(Math.random()));
            order.setStatus("unpaid");
            order.setDeleteStatus(0);
            order.setOrderSnapshot(Math.abs(random.nextInt()));

            System.out.println(order);
            orderDao.save(order);
        }
        if (!commit) {
            System.out.println(1 / 0);
        }
    }

    @Override
    public int countOrder() {
        return orderDao.count();
    }
}
