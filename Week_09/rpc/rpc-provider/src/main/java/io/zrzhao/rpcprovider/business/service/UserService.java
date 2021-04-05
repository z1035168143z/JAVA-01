package io.zrzhao.rpcprovider.business.service;

import io.zrzhao.rpcprovider.business.bo.User;

/**
 * @author zrzhao
 * @date 2021/3/27
 */
public interface UserService {

    User queryById(Long userId);

}
