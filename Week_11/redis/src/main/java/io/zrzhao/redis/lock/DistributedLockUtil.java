package io.zrzhao.redis.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author zrzhao
 * @date 2021/4/5
 */
@Component
public class DistributedLockUtil {

    @Autowired
    private DistributedLock distributedLock;

    public void compareAndSet(String key, ConcurrentOperator concurrentOperator) throws RuntimeException {
        String value = String.valueOf(Thread.currentThread().getId()) + System.currentTimeMillis();
        try {
            boolean lock = distributedLock.lock(key, value, TimeUnit.SECONDS, 3);
            if (!lock) {
                throw new RuntimeException("lock failure");
            }
            if (!concurrentOperator.continueExecution()) {
                return;
            }
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

            concurrentOperator.doSomething();
        } finally {
            distributedLock.releaseLock(key, value);
        }
    }


}
