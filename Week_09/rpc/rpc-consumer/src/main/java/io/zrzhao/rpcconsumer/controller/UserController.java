package io.zrzhao.rpcconsumer.controller;

import io.zrzhao.rpcprovider.business.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zrzhao
 * @date 2021/3/27
 */
public class UserController {

    @Autowired
    private UserService userService;

}
