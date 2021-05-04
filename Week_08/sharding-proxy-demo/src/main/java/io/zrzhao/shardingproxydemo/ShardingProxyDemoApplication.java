package io.zrzhao.shardingproxydemo;

import io.zrzhao.shardingproxydemo.order.entity.Order;
import io.zrzhao.shardingproxydemo.order.mapper.IOrder;
import io.zrzhao.shardingproxydemo.order.service.OrderService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.math.BigDecimal;
import java.util.Random;

@SpringBootApplication
public class ShardingProxyDemoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(ShardingProxyDemoApplication.class, args);

        OrderService orderService = applicationContext.getBean(OrderService.class);
        try {
            orderService.batchInsert(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(orderService.countOrder());

        try {
            orderService.batchInsert(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(orderService.countOrder());

    }

}
