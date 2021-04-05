package io.zrzhao.redis.datastorage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author zrzhao
 * @date 2021/4/5
 */
@Component
@ConditionalOnMissingBean(DistributedDataStorage.class)
public class RedisDistributedDataStorage implements DistributedDataStorage {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public long get(String key) {
        String value = redisTemplate.opsForValue().get(key);
        if (!StringUtils.hasText(value)) {
            return 0;
        }
        return Long.parseLong(value);
    }

    @Override
    public boolean set(String key, long value) {
        redisTemplate.opsForValue().set(key, String.valueOf(value));
        return true;
    }
}
