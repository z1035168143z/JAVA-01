package io.zrzhao.redis.datastorage;

/**
 * @author zrzhao
 * @date 2021/4/5
 */
public interface DistributedDataStorage {

    long get(String key);

    boolean set(String key, long value);

}
