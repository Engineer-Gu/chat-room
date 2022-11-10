package com.im.common;

/**
 * @author 顾天伟
 * 命令接口
 */
public interface Command {

    /**
     * 注册
     */
    int REGISTER = 0x01;

    /**
     * 登录
     */
    int LOGIN = 0x02;

    /**
     * 退出
     */
    int QUIT = 0x03;

    /**
     * 找回密码
     */
    int FIND_PASSWORD = 0x04;

    /**
     * 返回登录
     */
    int GO_BACK_LOGIN = 0x05;

    /**
     * 查看用户
     */
    int VIEW_USERS = 0x06;

    /**
     * 查看在线人员名单
     */
    int SHOW_ONLINE_PERSON_LIST = 0x07;

    /**
     * 私聊
     */
    int PRIVATE_CHAT = 0x08;

    /**
     * 群聊
     */
    int PUBLIC_CHAT = 0x09;


    /**
     * 账户注销
     */
    int CANCELLATION_ACCOUNT = 0x10;


    /**
     * 修改密码
     */
    int UPDATE_PASSWORD = 0x11;
}
