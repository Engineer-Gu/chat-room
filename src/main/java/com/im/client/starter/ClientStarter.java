package com.im.client.starter;

import com.im.client.menu.MenuManager;
import com.im.client.page.Page;
import com.im.client.service.SystemService;

/**
 * @author 老顾
 * @title: ClientStarter
 * @projectName chat-room
 * @email: 1437594522@qq.com
 * @date 2022/11/1 18:57
 */
public class ClientStarter{

    public static void main(String[] args) throws Exception {
        System.out.println("欢迎来到只因情聊天室!");
        Page.showInterface(MenuManager.FONT_PAGE_MENUS);
    }

}
