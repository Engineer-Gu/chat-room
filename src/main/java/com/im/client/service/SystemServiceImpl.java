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
 * @title: SystemServiceImpl
 * @projectName chat-room
 * @email: 1437594522@qq.com
 * @date 2022/11/1 16:49
 */
public class SystemServiceImpl {

    private static final String IS = "是";

    /**
     * 查看在线人员名单
     */
    public static void findOnlineUsers() {

    }

    /**
     * 私聊
     */
    public static void privateChat() {

    }

    /**
     * 群聊
     */
    public static void publicChat() {

    }

    /**
     * 注销：拿到当前登录的用户名，再根据这个用户名去删除，删除完返回到首页
     */
    public static void cancellationAccount() {
        String username = InputUtil.getInputText("请输入当前登录的用户名：");
        String msg = InputUtil.getInputText("是否对本账号进行注销？(是/否)");
        boolean msgMatches = Pattern.matches("[\u662f,\u5426]+",msg);
        User userInfo = new User();
        userInfo.deleteUser(username,msg);
        Menu[] homePageMenus;
        if (!msgMatches){
            System.out.println("注销失败，请输入(是/否)进行确认");
            homePageMenus = MenuManager.HOME_PAGE_MENUS;
            Page.showInterface(homePageMenus);
        }else {
            User user = MessageSender.sendMsg(Command.CANCELLATION_ACCOUNT,userInfo);
            if (user!=null && IS.equals(msg)){
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
    public static void updatePassword() {
        String username = InputUtil.getInputText("请输入当前登录的用户名：");
        String oldPassword = InputUtil.getInputText("输入原来的密码：");
        String newPassword = InputUtil.getInputText("输入要修改的新密码：");
        boolean userNameMatches = Pattern.matches("^[a-zA-Z0-9]{5,20}$", username);
        boolean oldPassWordMatches = Pattern.matches("^[a-zA-Z0-9]{5,20}$", oldPassword);
        boolean newPassWordMatches = Pattern.matches("^[a-zA-Z0-9]{5,20}$", newPassword);
        User user = new User();
        user.updatePassword(username,oldPassword,newPassword);
        Menu[] homePageMenus;
        if (!userNameMatches  || !oldPassWordMatches|| !newPassWordMatches) {
            System.out.println("用户名|密码的格式输入错误，请重新输入！");
        }else {
            User updatePassword = MessageSender.sendMsg(Command.UPDATE_PASSWORD, user);
            //如果为空，修改失败，如果输入的密码不等于原先的密码，或者用户名不正确，提示
            if (updatePassword == null){
                System.out.println("修改密码失败，请稍后重试！");
                homePageMenus = MenuManager.HOME_PAGE_MENUS;
                Page.showInterface(homePageMenus);
            }else if (username.equals(updatePassword.getUserName())){
                System.out.println("修改密码成功，请重新登录！");
                homePageMenus = MenuManager.FONT_PAGE_MENUS;
                Page.showInterface(homePageMenus);
            }else {
                System.out.println("修改密码失败，不能修改其他人的密码！");
            }
        }
    }
}
