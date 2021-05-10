package io.zrzhao.rpcconsumer;

import io.zrzhao.rpcprovider.bo.User;
import io.zrzhao.rpcprovider.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@Slf4j
public class RpcConsumerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(RpcConsumerApplication.class, args);

        UserService userService = applicationContext.getBean(UserService.class);
        User user = userService.queryById(10L);

        log.info("user={}", user);
    }

}
