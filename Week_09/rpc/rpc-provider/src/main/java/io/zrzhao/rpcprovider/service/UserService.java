package io.zrzhao.rpcprovider.service;

import io.zrzhao.rpcprovider.bo.User;

/**
 * @author zrzhao
 * @date 2021/3/27
 */
public interface UserService {

    User queryById(Long userId);

}
