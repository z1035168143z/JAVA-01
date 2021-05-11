package io.zrzhao.rpc.service.impl;

import io.zrzhao.rpc.bo.User;
import io.zrzhao.rpc.service.UserService;
import io.zrzhao.rpc.annotation.Provider;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zrzhao
 * @date 2021/3/27
 */
@Component
@Provider
public class UserServiceImpl implements UserService {

    Map<Long, User> userMap = new HashMap<>();

    @Override
    public User queryById(Long userId) {
        return userMap.getOrDefault(userId, new User(userId, "张三" + userId + "号"));
    }
}
