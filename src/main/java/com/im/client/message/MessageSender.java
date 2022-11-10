package com.im.client.message;

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

    private static final String IP = "localhost";

    private static final int PORT = 8848;

    /**
     * 客户端发送消息
     * @param command
     * @param data
     * @param <T>
     * @param <V>
     * @return
     */
    public static <T,V> T sendMsg(int command, V data){
        try {
            Socket socket = new Socket(IP, PORT);
            Message<V> msg = new Message<>(command, data);
            SocketUtil.sendMsg(socket, msg);
            return SocketUtil.receiveMsg(socket);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
