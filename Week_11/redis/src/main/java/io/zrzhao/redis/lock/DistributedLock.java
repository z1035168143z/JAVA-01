package io.zrzhao.redis.lock;

import java.util.concurrent.TimeUnit;

/**
 * @author zrzhao
 * @date 2021/4/5
 */
public interface DistributedLock {

    /**
     * 获取锁
     *
     * @return
     */
    boolean lock(String key, String value);

    /**
     * 获取锁
     *
     * @param key       key
     * @param value     锁值
     * @param timeUnit  时间
     * @param duration  时间
     * @return
     */
    boolean lock(String key, String value, TimeUnit timeUnit, long duration);

    /**
     * 续命
     *
     * @param key
     * @param timeUnit
     * @param duration
     * @return
     */
    boolean expire(String key, TimeUnit timeUnit, int duration);

    /**
     * 释放锁
     *
     * @param key
     * @param value
     * @return
     */
    boolean releaseLock(String key, String value);

}
