package io.zrzhao.redis.datastorage;

import io.zrzhao.redis.lock.DistributedLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author zrzhao
 * @date 2021/4/5
 */
@Component
public class DistributedCounterUtil {

    @Autowired
    private DistributedLock distributedLock;

    /**
     * 增加key对应的数
     *
     * @param key
     * @return the number after increase
     */
    public long increase(String key, ConcurrentCounterOperator operator) {
        String value = String.valueOf(Thread.currentThread().getId()) + System.currentTimeMillis();
        try {
            boolean lock = distributedLock.lock(key, value, TimeUnit.SECONDS, 3);
            if (!lock) {
                throw new RuntimeException("lock failure");
            }
            long num = operator.getNum() + 1;
            // 守护线程
            Thread daemonThread = new Thread(() -> {
                while (true) {
                    boolean expire = distributedLock.expire(key, TimeUnit.SECONDS, 3);
                    if (!expire) {
                        return;
                    }
                    try {
                        TimeUnit.MILLISECONDS.sleep(2500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            daemonThread.setDaemon(true);
            daemonThread.start();

            operator.setNum(num);

            return num;
        } finally {
            distributedLock.releaseLock(key, value);
        }
    }


}
