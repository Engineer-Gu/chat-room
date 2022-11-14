package com.im.server.service;


import com.im.common.entity.User;
import com.im.server.mapper.ServerMapper;
import com.im.common.Message;

/**
 * @author 老顾
 * @title: UserController
 * @projectName chat-room
 * @email: 1437594522@qq.com
 * @date 2022/11/1 16:28
 */
public class ServerService {

    /**
     * 增加用户
     * @param msg
     * @return
     */
    public static boolean insertUser(Message msg){
        return ServerMapper.insertUser(msg);
    }

    /**
     * 查询用户
     * @param user
     * @return
     */
    public static User selectUserName(User user){
        return ServerMapper.selectUserName(user);
    }

    /**
     * 用户登录
     * @param msg
     * @return
     */
    public static User login(Message msg){
        return ServerMapper.login(msg);
    }

    /**
     * 删除用户
     */
    public static void deleteUser(){
        ServerMapper.deleteUser();
    }

    /**
     * 修改用户
     * @param msg
     */
    public static void updateUserPassword(Message msg){
        ServerMapper.updateUserPassword(msg);
    }

    public static User findOnlineUser(Message msg) {
        return ServerMapper.findOnlineUser(msg);
    }
}
