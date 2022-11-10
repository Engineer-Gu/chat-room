package com.im.server.starter;

import com.im.server.controller.ServerController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author 老顾
 * @title: ServerStarter
 * @projectName chat-room
 * @email: 1437594522@qq.com
 * @date 2022/11/1 19:10
 */
public class ServerStarter {

    private final ServerSocket serverSocket;

    public ServerStarter(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    private void start() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                new Thread(new ServerController(socket)).start();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        System.out.println("欢迎来到6-308小组开发的聊天室");
        try {
            ServerStarter serverStarter = new ServerStarter(8848);
            serverStarter.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
