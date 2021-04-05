package io.zrzhao.rpcprovider.business.service.impl;

import io.zrzhao.rpcprovider.business.bo.User;
import io.zrzhao.rpcprovider.business.service.UserService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zrzhao
 * @date 2021/3/27
 */
public class UserServiceImpl implements UserService {

    Map<Long, User> userMap = new HashMap<>();

    @Override
    public User queryById(Long userId) {
        return userMap.getOrDefault(userId, new User(userId, "张三" + userId + "号"));
    }
}
