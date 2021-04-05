package com.zrzhao.mysql;

import com.zrzhao.mysql.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MysqlApplication {


    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(MysqlApplication.class, args);

        OrderService orderService = applicationContext.getBean(OrderService.class);
        orderService.readOrderByOrderNum("1");
        orderService.getOrderByOrderNum("1");
    }

}
