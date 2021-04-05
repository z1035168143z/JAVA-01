package io.zrzhao.redis.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @author zrzhao
 * @date 2021/4/5
 */
@Component
@ConditionalOnMissingBean(DistributedLock.class)
public class RedisDistributedLock implements DistributedLock {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public boolean lock(String key, String value) {
        Boolean result = redisTemplate.opsForValue().setIfAbsent(key, value);
        return result != null && result;
    }

    @Override
    public boolean lock(String key, String value, TimeUnit timeUnit, long duration) {
        Boolean result = redisTemplate.opsForValue().setIfAbsent(key, value, duration, timeUnit);
        return result != null && result;
    }

    @Override
    public boolean expire(String key, TimeUnit timeUnit, int duration) {
        Boolean result = redisTemplate.expire(key, duration, timeUnit);
        return result != null && result;
    }

    @Override
    public boolean releaseLock(String key, String value) {
        String luaScript = "local in = ARGV[1] local curr=redis.call('get', KEYS[1]) " +
                " if in==curr " +
                " then redis.call('del', KEYS[1]) end return 'OK' ";
        RedisScript<String> redisScript = RedisScript.of(luaScript);
        String execute = redisTemplate.execute(redisScript, Collections.singletonList(key), Collections.singleton(value));
        return "OK".equals(execute);
    }
}
