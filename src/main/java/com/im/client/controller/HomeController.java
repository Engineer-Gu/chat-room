package com.im.client.controller;


import com.im.client.service.HomeServiceImpl;

/**
 * @author 老顾
 * @title: UserController
 * @projectName chat-room
 * @description 控制层，调用业务层
 * @email: 1437594522@qq.com
 * @date 2022/11/1 16:44
 */
public class HomeController {

    /**
     * 用户注册
     */
    public static void registerUser(){
        HomeServiceImpl.registerUser();
    }

    /**
     * 用户登录
     */
    public static void loginUser(){
        HomeServiceImpl.loginUser();
    }

    /**
     * 找回密码
     */
    public static void findPassword(){
        HomeServiceImpl.findPassword();
    }
}
