package com.im.client.service;

import com.im.client.menu.Menu;
import com.im.client.menu.MenuManager;
import com.im.client.message.MessageSender;
import com.im.client.page.Page;
import com.im.client.starter.ClientStarter;
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
public class HomeServiceImpl{

    private static final Integer COUNT = 3;

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
     * 用户登录
     * @return
     */
    public static void loginUser() {
        int count = 0;
        while (count < COUNT){
            String username = InputUtil.getInputText("请输入用户名：");
            String password = InputUtil.getInputText("请输入密码：");
            String email = InputUtil.getInputText("请输入邮箱：");
            boolean userNameMatches = Pattern.matches("^[a-zA-Z0-9]{5,20}$", username);
            boolean passWordMatches = Pattern.matches("^[a-zA-Z0-9]{5,20}$", password);
            boolean emailMatches = Pattern.matches("[0-9]{5,11}@qq.com", email);
            User user = new User(username, password,email);
            if (!userNameMatches || !passWordMatches || !emailMatches) {
                System.out.println("用户名|密码|邮箱的格式输入错误，请重新登录！");
            }else {
                //发送数据给服务端
                User userLoginInfo = MessageSender.sendMsg(Command.LOGIN, user);
                Menu[] homePageMenus;
                //登录校验
                if(userLoginInfo == null){
                    System.out.println("用户不存在，请重新登录");
                }else if(username.equals(userLoginInfo.getUserName())&&password.equals(userLoginInfo.getPassword())&&email.equals(userLoginInfo.getEmail())){
                    System.out.println("登录成功！进入聊天室主页");
                    homePageMenus = MenuManager.HOME_PAGE_MENUS;
                    Page.showInterface(homePageMenus);
                }else {
                    System.out.println("用户名|密码|邮箱输入错误！请重新输入");
                }
            }
            count++;
            if (count == COUNT) {
                System.out.println("您输入的次数过多，将退出本聊天室");
                System.exit(0);
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
