package com.im.common.thread;

import com.im.common.entity.User;
import com.im.common.message.Command;
import com.im.common.utils.SocketUtil;
import java.net.Socket;

/**
 * @author 老顾
 * @title: ReceiveThread
 * @projectName chat-room
 * @description 接收线程，用来接收服务端发送的消息
 * @email: 1437594522@qq.com
 * @date 2022/11/15 19:13
 */
public class ReceiveThread extends Thread {

    private Socket socket;
    private static final String IS = "是";
    public ReceiveThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        //while循环一直连接
        while (true){
            try {
                //如果服务器没有发送的User对象，线程会堵塞在这里
                User user = SocketUtil.receiveMsg(socket);
                //在线人员名单
                assert user != null;
                //接收在线人员列表数据
                if (user.getMesType() == Command.SHOW_ONLINE_USERS_LIST) {
                    //取出在线列表信息，并显示
                    String[] onlineUsers = user.getUserName().split(" ");
                    System.out.println("=========在线用户人员列表========");
                    for (String onlineUser : onlineUsers) {
                        System.out.println("当前在线用户【" + onlineUser+"】");
                    }
                }
                //接收私聊数据
                if (user.getMesType() == Command.PRIVATE_CHAT) {
                    //显示信息
                    System.out.println("收到来自用户【"+user.getUserName() +"】发来的一条消息，内容是："+ user.getContent());
                }
                //接收群聊数据
                if (user.getMesType() == Command.PUBLIC_CHAT) {
                    System.out.println("收到来自用户【"+user.getUserName() +"】群发的一条消息，内容是："+ user.getContent());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }
}
