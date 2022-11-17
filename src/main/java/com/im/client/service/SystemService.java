package com.im.client.service;

import com.im.client.menu.Menu;
import com.im.client.menu.MenuManager;
import com.im.client.message.MessageSender;
import com.im.client.page.Page;
import com.im.common.message.Command;
import com.im.common.entity.User;
import com.im.common.utils.InputUtil;
import com.im.server.service.ServerService;

import java.util.regex.Pattern;

/**
 * @author 老顾
 * @title: SystemServiceImpl
 * @projectName chat-room
 * @email: 1437594522@qq.com
 * @date 2022/11/1 16:49
 */
public class SystemService{

    private static final String IS = "是";

    private static final Integer COUNT = 3;

    private static User user = new User();

    private  static String userName;

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        SystemService.userName = userName;
    }

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
        //把信息存到user里
        user.setUserName(username);
        user.setPassword(password);
        user.setEmail(email);
        //查询数据库的用户是否存在
        User findUser = ServerService.selectUserName(user);
        if (!userNameMatches || !passWordMatches || !emailMatches) {
            System.out.println("用户名|密码|邮箱格式输入错误，请重新注册！");
        }else if (findUser != null){
            System.out.println("账号已存在，请重新注册");
            Page.showInterface(MenuManager.FONT_PAGE_MENUS);
        }else {
            //发送数据给服务端
            MessageSender.sendOn(Command.REGISTER, user);
            System.out.println("注册成功！");
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
        user.setUserName(userName);
        user.setEmail(email);
        //查询用户是否存在
        User findUser = ServerService.selectUserName(user);
        if (!userNameMatches  || !emailMatches) {
            System.out.println("用户名|邮箱的格式输入错误，请重新输入！");
        }else {
            if(findUser == null){
                System.out.println("用户不存在，请先注册！");
            }else {
                System.out.println("您的密码是：");
                System.out.println(findUser.getPassword());
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
            userName = username;
            user.setUserName(userName);
            user.setPassword(password);
            user.setEmail(email);
            if (!userNameMatches || !passWordMatches || !emailMatches) {
                System.out.println("用户名|密码|邮箱的格式输入错误，请重新登录！");
            }else {
                //发送数据给服务端
                MessageSender.sendOn(Command.LOGIN, user);
                //查询用户是否存在
                User findUser = ServerService.selectUserName(user);
                //登录校验
                if(findUser == null){
                    System.out.println("用户不存在，请重新登录");
                }else if(username.equals(findUser.getUserName())
                        &&password.equals(findUser.getPassword())
                        &&(email.equals(findUser.getEmail()))){
                    System.out.println("登录成功！进入聊天室主页");
                    //调用发送器，把socket传入子线程
                    MessageSender.chatThread();
                    Page.showInterface(MenuManager.HOME_PAGE_MENUS);
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
     * 查看在线人员名单
     */
    public static void findOnlineUsers() {
        //向服务端传入菜单选项
        user.setMesType(Command.SHOW_ONLINE_USERS_LIST);
        MessageSender.sendOn(Command.SHOW_ONLINE_USERS_LIST, user);
    }

    /**
     * 私聊
     */
    public static void privateChat() {
        System.out.println("开始聊天吧！");
        String targetUserName = InputUtil.getInputText("请输入对方姓名（在线）：");
        String content = InputUtil.getInputText("请输入内容：");
        //正则校验
        boolean userNameMatches = Pattern.matches("^[a-zA-Z0-9]{5,20}$", targetUserName);
        //把发送的数据存到message对象里
        user.setUserName(userName);
        user.setTargetUser(targetUserName);
        user.setContent(content);
        user.setMesType(Command.PRIVATE_CHAT);
        user.setMsg(IS);
        //检验
        if (!userNameMatches) {
            System.out.println("输入的用户名不正确");
        } else {
            //把数据发送给服务端
            MessageSender.sendOn(Command.PRIVATE_CHAT, user);
            System.out.println("已向【" + targetUserName + "】发送一条消息");
        }
    }

    /**
     * 群聊
     */
    public static void publicChat() {
        String publicChatMessage = InputUtil.getInputText("请发送要群聊的消息：");
        user.setUserName(userName);
        user.setContent(publicChatMessage);
        user.setMesType(Command.PUBLIC_CHAT);
        MessageSender.sendOn(Command.PUBLIC_CHAT, user);
        System.out.println("已【群发】一条消息");
    }

    /**
     * 用户注销
     */
    public static void cancellationAccount() {
        String cancellationAccount = InputUtil.getInputText("是否对本账号进行注销？(是/否)：");
        //正则校验
        boolean msgMatches = Pattern.matches("[\u662f,\u5426]+", cancellationAccount);
        user.setUserName(userName);
        user.setMsg(cancellationAccount);
        user.setMesType(Command.CANCELLATION_ACCOUNT);
        //不满足正则则退回主页
        if (!msgMatches){
            System.out.println("注销失败，请输入(是/否)进行确认：");
            Page.showInterface(MenuManager.HOME_PAGE_MENUS);
        }else if (IS.equals(cancellationAccount)){
            MessageSender.sendOn(Command.CANCELLATION_ACCOUNT, user);
        }else {
            System.out.println("注销失败，退回主页");
            Page.showInterface(MenuManager.HOME_PAGE_MENUS);
        }
    }

    /**
     * 修改密码
     */
    public static void updatePassword() {
        String oldPassword = InputUtil.getInputText("输入原来的密码：");
        String newPassword = InputUtil.getInputText("输入要修改的新密码：");
        boolean oldPassWordMatches = Pattern.matches("^[a-zA-Z0-9]{5,20}$", oldPassword);
        boolean newPassWordMatches = Pattern.matches("^[a-zA-Z0-9]{5,20}$", newPassword);
        //把输入的信息注入到User的构造器
        user.setPassword(oldPassword);
        user.setNewPassword(newPassword);
        user.setMesType(Command.UPDATE_PASSWORD);
        User findUser = ServerService.selectUserName(user);
        if (!oldPassWordMatches || !newPassWordMatches) {
            System.out.println("用户名|密码的格式输入错误，请重新输入！");
        }else if (findUser.getPassword().equals(oldPassword)){
            MessageSender.sendOn(Command.UPDATE_PASSWORD, user);
        }else {
            System.out.println("原来密码不一致，修改密码失败，返回主页！");
            Page.showInterface(MenuManager.HOME_PAGE_MENUS);
        }
    }

    /**
     * 退出登录
     */
    public static void logout() {
        user.setMesType(Command.GO_BACK_LOGIN);
        MessageSender.sendOn(Command.GO_BACK_LOGIN, user);
    }
}
