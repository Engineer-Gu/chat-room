package com.im.client.page;

import com.im.client.controller.SystemController;
import com.im.client.menu.Menu;
import com.im.client.menu.MenuManager;
import com.im.client.controller.HomeController;
import com.im.common.Command;
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
        int number = InputUtil.getInputInteger("请选择菜单编号：", 1, menus.length);
        Menu select = menus[number-1];
        switch (select.getCommand()){
            //注册
            case Command.REGISTER:
                HomeController.registerUser();
                showInterface(menus);
                break;
            //登录
            case Command.LOGIN:
                HomeController.loginUser();
                showInterface(menus);
                break;
            //退出登录
            case Command.GO_BACK_LOGIN:
                showInterface(MenuManager.FONT_PAGE_MENUS);
                System.out.println("退出登录，跳转到首页");
                break;
            //退出首页
            case Command.QUIT:
                System.out.println("感谢使用本聊天室，欢迎下次再来~");
                System.exit(0);
                break;
            //查找用户密码
            case Command.FIND_PASSWORD:
                HomeController.findPassword();
                showInterface(menus);
                break;
            //账号注销
            case Command.CANCELLATION_ACCOUNT:
                SystemController.cancellationAccount();
                showInterface(MenuManager.FONT_PAGE_MENUS);
                break;
            //修改密码
            case Command.UPDATE_PASSWORD:
                SystemController.updatePassword();
                showInterface(menus);
                break;
        }
    }
}
