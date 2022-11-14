package com.im.client.message;

import com.im.client.service.SystemService;
import com.im.common.utils.SocketUtil;
import com.im.common.Message;

import java.io.IOException;
import java.net.Socket;

/**
 * @author 王如斌
 * @description 消息发送器
 * @projectName chat-room
 */
public class MessageSender {

    //本机IP地址
    private static final String IP = "127.0.0.1";

    //本地端口号
    private static final int PORT = 8848;

    /**
     * 客户端发送消息，并接收服务端返回的消息
     * @param command
     * @param value
     * @param <T>
     * @param <V>
     * @return
     */
    public static <T,V> T sendMsg(int command, V value){
        try {
            Socket socket = new Socket(IP, PORT);
            Message<T,V> msg = new Message<>();
            msg.setCommand(command);
            msg.setValue(value);
            //发送消息给服务端，消息包括自己的socket和发送的数据
            SocketUtil.sendMsg(socket, msg);
            //接收服务端的消息和socket
            return SocketUtil.receiveMsg(socket);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static <T,V> T sendMessage(Socket socket, V value){
        Message<T,V> msg = new Message<>();
        msg.setSocket(socket);
        msg.setValue(value);
        //发送消息给服务端，消息包括自己的socket和发送的数据
        SocketUtil.send(socket, msg);
        return SocketUtil.receive(socket);
    }
}
