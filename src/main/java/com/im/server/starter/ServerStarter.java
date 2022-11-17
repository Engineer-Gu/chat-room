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
public class ServerStarter{

    private final ServerSocket serverSocket;

    public ServerStarter(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    /**
     * Socket accept()-----调用该方法后用Socket引用类型来接收
     * 该方法是一个阻塞方法，调用会程序会卡住，直到一个客户端与服务端建立连接
     */
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
        System.out.println("欢迎来到只因情聊天室！！");
        try {
            //开启监听的端口号
            ServerStarter serverStarter = new ServerStarter(8848);
            //调用方法，启动一个线程处理与该客户端交互
            serverStarter.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
