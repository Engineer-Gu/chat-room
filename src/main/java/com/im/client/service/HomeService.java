package com.im.client.service;

import com.im.client.message.MessageSender;
import com.im.common.Command;
import com.im.common.entity.User;
import com.im.common.utils.InputUtil;

import java.util.regex.Pattern;

/**
 * @author 老顾
 * @title: UserServiceImpl
 * @projectName chat-room
 * @description 业务层，写功能的逻辑
 * @email: 1437594522@qq.com
 * @date 2022/11/1 16:44
 */
public class HomeService{

    /**
     * 用户注册
     */
    public static void registerUser() {
        //输入用户名，密码
        String username = InputUtil.getInputText("请输入用户名：");
        String password = InputUtil.getInputText("请输入密码：");
        String email = InputUtil.getInputText("请输入邮箱：");
        boolean userNameMatches = Pattern.matches("^[a-zA-Z0-9]{5,20}$", username);
        boolean passWordMatches = Pattern.matches("^[a-zA-Z0-9]{5,20}$", password);
        boolean emailMatches = Pattern.matches("[0-9]{5,11}@qq.com", email);
        User user = new User(username, password,email);
        if (!userNameMatches || !passWordMatches || !emailMatches) {
            System.out.println("用户名|密码|邮箱格式输入错误，请重新注册！");
        }else {
            //发送数据给服务端
            Integer result = MessageSender.sendMsg(Command.REGISTER, user);
            //注册校验
            if(result == null || result == 0){
                System.out.println("注册失败，请重新注册！");
            } else if(result == 1){
                System.out.println("注册成功！");
            } else {
                System.out.println("注册失败，账号已存在，请重新注册！");
            }
        }
    }

    /**
     * 找回密码
     */
    public static void findPassword() {
        String userName = InputUtil.getInputText("请输入用户名：");
        String email = InputUtil.getInputText("请输入邮箱：");
        boolean userNameMatches = Pattern.matches("^[a-zA-Z0-9]{5,20}$", userName);
        boolean emailMatches = Pattern.matches("[0-9]{5,11}@qq.com", email);
        User user1 = new User();
        user1.findPassword(userName,email);
        if (!userNameMatches  || !emailMatches) {
            System.out.println("用户名|邮箱的格式输入错误，请重新登录！");
        }else {
            User userPassword = MessageSender.sendMsg(Command.FIND_PASSWORD,user1);
            if(userPassword == null){
                System.out.println("用户不存在，请先注册！");
            }else {
                System.out.println("您的密码是：");
                System.out.println(userPassword.getPassword());
            }
        }
    }
}
