package io.zrzhao.rpc.service;

import io.zrzhao.rpc.bo.User;

/**
 * @author zrzhao
 * @date 2021/3/27
 */
public interface UserService {

    User queryById(Long userId);

}
