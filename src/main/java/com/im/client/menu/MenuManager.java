package com.im.client.menu;

import com.im.common.message.Command;

import java.util.Arrays;

/**
 * @author 老顾
 * @title: MenuManager
 * @projectName chat-room
 * @email: 1437594522@qq.com
 * @date 2022/11/1 18:59
 */
public class MenuManager {

    /**
     * 展示给定的菜单数组
     * @param menus
     */
    public static void showMenu(Menu[] menus){
        System.out.println("===========================");
        Arrays.stream(menus).forEach(System.out::println);
        System.out.println("===========================");
    }

    /**
     * 首页菜单
     */
    public static final Menu[] FONT_PAGE_MENUS = {
            new Menu(1, "注册", Command.REGISTER),
            new Menu(2, "登录", Command.LOGIN),
            new Menu(3, "找回密码", Command.FIND_PASSWORD),
            new Menu(4, "退出", Command.QUIT),
    };

    /**
     * 主页菜单
     */
    public static final Menu[] HOME_PAGE_MENUS = {
            new Menu(1, "查看在线人员名单", Command.SHOW_ONLINE_USERS_LIST),
            new Menu(2, "私聊", Command.PRIVATE_CHAT),
            new Menu(3, "群聊", Command.PUBLIC_CHAT),
            new Menu(4, "账户注销", Command.CANCELLATION_ACCOUNT),
            new Menu(5, "修改密码", Command.UPDATE_PASSWORD),
            new Menu(6, "退出", Command.GO_BACK_LOGIN),
    };
}
