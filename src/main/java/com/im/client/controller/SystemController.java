package com.im.client.controller;


import com.im.client.service.SystemServiceImpl;

/**
 * @author 老顾
 * @title: SystemController
 * @projectName chat-room
 * @email: 1437594522@qq.com
 * @date 2022/11/1 16:48
 */
public class SystemController {

    /**
     * 查看在线人员名单
     */
    public static void findOnlineUsers(){
        SystemServiceImpl.findOnlineUsers();
    }

    /**
     * 私聊
     */
    public static void privateChat(){
        SystemServiceImpl.privateChat();
    }

    /**
     * 群聊
     */
    public static void publicChat(){
        SystemServiceImpl.publicChat();
    }

    /**
     * 账户注销
     */
    public static void cancellationAccount(){
        SystemServiceImpl.cancellationAccount();
    }

    /**
     * 修改密码
     */
    public static void updatePassword(){
        SystemServiceImpl.updatePassword();
    }
}
