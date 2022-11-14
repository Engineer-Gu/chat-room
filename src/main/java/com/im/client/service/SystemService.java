package com.im.client.service;

import com.im.client.menu.Menu;
import com.im.client.menu.MenuManager;
import com.im.client.message.MessageSender;
import com.im.client.page.Page;
import com.im.common.Command;
import com.im.common.entity.User;
import com.im.common.managesocket.ManageClientConnectServerSocket;
import com.im.common.utils.InputUtil;
import com.im.common.utils.SocketUtil;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

/**
 * @author 老顾
 * @title: SystemServiceImpl
 * @projectName chat-room
 * @email: 1437594522@qq.com
 * @date 2022/11/1 16:49
 */
public class SystemService implements Runnable, Serializable {

    private static final long serialVersionUID = 1L;

    private static final String IS = "是";

    private static final Integer COUNT = 3;

    private static User user = new User();

    private static Socket socket;

    static {
        try {
            socket = new Socket(InetAddress.getByName("127.0.0.1"),8848);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private  static String userName;

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        SystemService.userName = userName;
    }

    public SystemService() {

    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {
        try {
            loginUser();
            privateChat();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 用户登录
     * @return
     */
    public static void loginUser() throws Exception {
        int count = 0;
        while (count < COUNT){
            String username = InputUtil.getInputText("请输入用户名：");
            String password = InputUtil.getInputText("请输入密码：");
            String email = InputUtil.getInputText("请输入邮箱：");
            boolean userNameMatches = Pattern.matches("^[a-zA-Z0-9]{5,20}$", username);
            boolean passWordMatches = Pattern.matches("^[a-zA-Z0-9]{5,20}$", password);
            boolean emailMatches = Pattern.matches("[0-9]{5,11}@qq.com", email);
            userName = username;
            user.setUserName(userName);
            user.setPassword(password);
            user.setEmail(email);
            if (!userNameMatches || !passWordMatches || !emailMatches) {
                System.out.println("用户名|密码|邮箱的格式输入错误，请重新登录！");
            }else {
                //发送数据给服务端，返回回来的数据存到userInfo里
                User userInfo =  MessageSender.sendMsg(Command.LOGIN, user);
                //登录校验
                if(userInfo == null){
                    System.out.println("用户不存在，请重新登录");
                }else if(username.equals(userInfo.getUserName())
                        &&password.equals(userInfo.getPassword())
                        &&(email.equals(userInfo.getEmail()))){
                    System.out.println("登录成功！进入聊天室主页");
                    //启动线程
                    //客户端可能会有多个Socket线程，为了方便管理和扩展，可以放在集合中。
                    ManageClientConnectServerSocket.addClientConnectServerSocket(userInfo.getUserName(), socket);
                    // 展示主页菜单
                    Menu[] homePageMenus;
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
     * 退出登录
     */
    public static void logout() throws Exception {
        boolean logout = MessageSender.sendMsg(Command.GO_BACK_LOGIN,user);
        if (logout){
            Page.showInterface(MenuManager.FONT_PAGE_MENUS);
        }
    }
    /**
     * 查看在线人员名单
     */
    public static void findOnlineUsers() throws Exception {
        //向服务端传入菜单选项
        String onLineUserList = MessageSender.sendMsg(Command.SHOW_ONLINE_USERS_LIST, user);
        //断言校验
        assert onLineUserList != null;
        String[] onlineUsers = onLineUserList.split(" ");
        System.out.println("========在线用户列表========");
        for (String onlineUser : onlineUsers) {
            System.out.println("用户：" + onlineUser);
        }
        Menu[] homePageMenus;
        homePageMenus = MenuManager.HOME_PAGE_MENUS;
        Page.showInterface(homePageMenus);
    }

    /**
     * 私聊
     */
    public static void privateChat() throws Exception {
        System.out.println("开始聊天吧！");
        String username = InputUtil.getInputText("请输入对方姓名（在线）：");
        String content = InputUtil.getInputText("请输入内容：");
        //发给服务端
        boolean userNameMatches = Pattern.matches("^[a-zA-Z0-9]{5,20}$", username);
        //把发送的数据存到user对象里
        user.setUserName(username);
        user.setContent(content);
        socket =  ManageClientConnectServerSocket.getSocket(userName);
        if (socket == null){
            System.out.println("空的");
        }
        //检验
        if (!userNameMatches) {
            System.out.println("输入的用户名不正确");
        } else {
            //把数据发送给服务端，并接受服务端返回的数据
            String message = MessageSender.sendMsg(Command.PRIVATE_CHAT, user);
            if (message == null){
                System.out.println("对方不在线!");
                Menu[] homePageMenus;
                homePageMenus = MenuManager.HOME_PAGE_MENUS;
                Page.showInterface(homePageMenus);
            }else{
                System.out.println("已向【" + username + "】发送一条消息");
                System.out.println(message);
                //发送消息给目标客户端
//                String targetMessage = MessageSender.sendMessage(socket, message);
                Menu[] homePageMenus;
                homePageMenus = MenuManager.HOME_PAGE_MENUS;
                Page.showInterface(homePageMenus);
            }
        }
    }

    /**
     * 群聊
     */
    public static void publicChat() {

    }

    /**
     * 用户注销
     */
    public static void cancellationAccount() throws Exception {
        //输入
        String msg = InputUtil.getInputText("是否对本账号进行注销？(是/否)");
        //正则校验
        boolean msgMatches = Pattern.matches("[\u662f,\u5426]+",msg);
        User userInfo = new User();
        userInfo.deleteUser(msg);
        Menu[] homePageMenus;
        //不满足正则则退回主页
        if (!msgMatches){
            System.out.println("注销失败，请输入(是/否)进行确认");
            homePageMenus = MenuManager.HOME_PAGE_MENUS;
            Page.showInterface(homePageMenus);
        }else {
            String getUserame = MessageSender.sendMsg(Command.CANCELLATION_ACCOUNT, userInfo);
            if (getUserame != null && IS.equals(msg)){
                System.out.println("注销成功");
                System.out.println("您的账号已注销，跳转回首页");
            }else {
                System.out.println("注销失败");
                homePageMenus = MenuManager.HOME_PAGE_MENUS;
                Page.showInterface(homePageMenus);
            }
        }
    }

    /**
     * 修改密码
     */
    public static void updatePassword() throws Exception {
        String oldPassword = InputUtil.getInputText("输入原来的密码：");
        String newPassword = InputUtil.getInputText("输入要修改的新密码：");
        boolean oldPassWordMatches = Pattern.matches("^[a-zA-Z0-9]{5,20}$", oldPassword);
        boolean newPassWordMatches = Pattern.matches("^[a-zA-Z0-9]{5,20}$", newPassword);
        User user = new User();
        //把输入的信息注入到User的构造器
        user.setPassword(oldPassword);
        user.setNewPassword(newPassword);
        Menu[] homePageMenus;
        if (!oldPassWordMatches || !newPassWordMatches) {
            System.out.println("用户名|密码的格式输入错误，请重新输入！");
        }else {
            User updatePassword = MessageSender.sendMsg(Command.UPDATE_PASSWORD, user);
            //如果不为空，且修改的密码与是一致
            if (updatePassword != null){
                System.out.println("修改密码成功，请重新登录！");
                homePageMenus = MenuManager.FONT_PAGE_MENUS;
                Page.showInterface(homePageMenus);
            }else {
                System.out.println("修改密码失败，返回主页");
                homePageMenus = MenuManager.HOME_PAGE_MENUS;
                Page.showInterface(homePageMenus);
            }
        }
    }
}
