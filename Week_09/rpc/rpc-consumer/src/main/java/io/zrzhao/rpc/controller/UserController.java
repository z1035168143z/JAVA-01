package io.zrzhao.rpc.controller;

import io.zrzhao.rpc.bo.User;
import io.zrzhao.rpc.service.UserService;
import io.zrzhao.rpc.annotation.Consumer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zrzhao
 * @date 2021/3/27
 */
@RequestMapping("user")
@RestController
public class UserController {

    @Consumer
    private UserService userService;

    @GetMapping("/{userId}")
    public User findById(@PathVariable("userId") Long userId) {
        return userService.queryById(userId);
    }

}
