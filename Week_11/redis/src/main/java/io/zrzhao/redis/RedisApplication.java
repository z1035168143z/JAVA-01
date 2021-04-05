package io.zrzhao.redis;

import io.zrzhao.redis.datastorage.ConcurrentCounterOperator;
import io.zrzhao.redis.datastorage.DistributedCounterUtil;
import io.zrzhao.redis.datastorage.DistributedDataStorage;
import io.zrzhao.redis.lock.ConcurrentOperator;
import io.zrzhao.redis.lock.DistributedLockUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class RedisApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(RedisApplication.class, args);

        final DistributedLockUtil distributedLockUtil = applicationContext.getBean(DistributedLockUtil.class);
        String lockKey = "test.lock";
        int[] holder = new int[1];
        for (int i = 0; i < 100; i++) {
            new Thread(() -> distributedLockUtil.compareAndSet(lockKey, () -> {
                System.out.println(holder[0]++);
            })).start();
        }

        DistributedDataStorage distributedDataStorage = applicationContext.getBean(DistributedDataStorage.class);
        DistributedCounterUtil distributedCounterUtil = applicationContext.getBean(DistributedCounterUtil.class);
        String increaseKey = "test.lock.increase";
        String storageKey = "product.storage.1";
        distributedCounterUtil.increase(increaseKey, new ConcurrentCounterOperator() {
            @Override
            public long getNum() {
                return distributedDataStorage.get(storageKey);
            }

            @Override
            public boolean setNum(long num) {
                return distributedDataStorage.set(storageKey, num);
            }
        });
    }

}
