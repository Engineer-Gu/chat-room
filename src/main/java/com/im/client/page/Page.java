package com.im.client.page;

import com.im.client.menu.Menu;
import com.im.client.menu.MenuManager;
import com.im.client.service.SystemService;
import com.im.common.message.Command;
import com.im.common.utils.InputUtil;
/**
 * @author 老顾
 * @title: Page
 * @projectName chat-room
 * @email: 1437594522@qq.com
 * @date 2022/11/10 13:05
 */
public class Page {

    /**
     * 展示给定的菜单页面
     *
     * @param menus
     */
    public static void showInterface(Menu[] menus) {
        MenuManager.showMenu(menus);
        int number = InputUtil.getInputInteger(1, menus.length);
        Menu select = menus[number-1];
        switch (select.getCommand()){
            //注册
            case Command.REGISTER:
                SystemService.registerUser();
                showInterface(menus);
                break;
            //找回密码
            case Command.FIND_PASSWORD:
                SystemService.findPassword();
                showInterface(menus);
                break;
            //退出系统
            case Command.QUIT:
                System.out.println("感谢使用本聊天室，欢迎下次再来~");
                System.exit(0);
                break;
            //退出主页
            case Command.GO_BACK_LOGIN:
                SystemService.logout();
                System.out.println("退出登录，跳转到首页");
                showInterface(MenuManager.FONT_PAGE_MENUS);
                break;
            //登录
            case Command.LOGIN:
                SystemService.loginUser();
                break;
            //查看在线人员名单
            case Command.SHOW_ONLINE_USERS_LIST:
                SystemService.findOnlineUsers();
                showInterface(MenuManager.HOME_PAGE_MENUS);
                break;
            //私聊
            case Command.PRIVATE_CHAT:
                SystemService.privateChat();
                showInterface(MenuManager.HOME_PAGE_MENUS);
                break;
            //群聊
            case Command.PUBLIC_CHAT:
                SystemService.publicChat();
                showInterface(MenuManager.HOME_PAGE_MENUS);
                break;
                //账号注销
            case Command.CANCELLATION_ACCOUNT:
                SystemService.cancellationAccount();
                System.out.println("注销成功，跳转回首页");
                showInterface(MenuManager.FONT_PAGE_MENUS);
                break;
            //修改密码
            case Command.UPDATE_PASSWORD:
                SystemService.updatePassword();
                System.out.println("修改密码成功，跳转回首页");
                showInterface(MenuManager.FONT_PAGE_MENUS);
                break;
        }
    }
}
