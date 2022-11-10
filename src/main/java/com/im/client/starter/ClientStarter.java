package com.im.client.starter;

import com.im.client.menu.Menu;
import com.im.client.menu.MenuManager;
import com.im.client.service.HomeService;
import com.im.client.service.SystemService;
import com.im.common.Command;
import com.im.common.utils.InputUtil;

/**
 * @author 老顾
 * @title: ClientStarter
 * @projectName chat-room
 * @email: 1437594522@qq.com
 * @date 2022/11/1 18:57
 */
public class ClientStarter {

    public static void main(String[] args) {
        System.out.println("欢迎来到6-308小组开发的聊天室");
        showInterface(MenuManager.FONT_PAGE_MENUS);
    }

    /**
     * 展示给定的菜单
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
                HomeService.registerUser();
                showInterface(menus);
                break;
                //登录
            case Command.LOGIN:
                HomeService.loginUser();
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
                HomeService.findPassword();
                showInterface(menus);
                break;
                //账号注销
            case Command.CANCELLATION_ACCOUNT:
                SystemService.cancellationAccount();
                showInterface(MenuManager.FONT_PAGE_MENUS);
                break;
                //修改密码
            case Command.UPDATE_PASSWORD:
                SystemService.updatePassword();
                showInterface(menus);
                break;
        }
    }
}
