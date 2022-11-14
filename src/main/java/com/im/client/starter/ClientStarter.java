package com.im.client.starter;

import com.im.client.menu.MenuManager;
import com.im.client.page.Page;
import com.im.client.service.SystemService;
import com.im.server.controller.ServerController;
import com.im.server.starter.ServerStarter;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author 老顾
 * @title: ClientStarter
 * @projectName chat-room
 * @email: 1437594522@qq.com
 * @date 2022/11/1 18:57
 */
public class ClientStarter{

    /**
     * Socket accept()-----调用该方法后用Socket引用类型来接收
     * 该方法是一个阻塞方法，调用会程序会卡住，直到一个客户端与服务端建立连接
     */
    private static void start() {
        while (true) {
            new Thread(new SystemService()).start();
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("欢迎来到6-308小组开发的聊天室");
        Page.showInterface(MenuManager.FONT_PAGE_MENUS);
        //调用方法，启动一个线程处理与该客户端交互
        ClientStarter.start();
    }

}
