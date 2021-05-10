package io.zrzhao.rpcprovider.service.impl;

import io.zrzhao.rpcprovider.bo.User;
import io.zrzhao.rpcprovider.service.UserService;
import io.zrzhao.rpcregister.annotation.Provider;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zrzhao
 * @date 2021/3/27
 */
@Provider
public class UserServiceImpl implements UserService {

    Map<Long, User> userMap = new HashMap<>();

    @Override
    public User queryById(Long userId) {
        return userMap.getOrDefault(userId, new User(userId, "张三" + userId + "号"));
    }
}
