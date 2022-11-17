package com.im.client.message;

import com.im.common.thread.ReceiveThread;
import com.im.common.utils.SocketUtil;
import com.im.common.message.Message;
import java.net.Socket;

/**
 * @author 王如斌
 * @description 消息发送器
 * @projectName chat-room
 */
public class MessageSender {

    /**
     * 客户端的socket
     */
    private static Socket socket;

    static {
        try {
            socket = new Socket("127.0.0.1", 8848);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 客户端发数据给服务端而不接收服务端数据（一直发）
     * @param command
     * @param value
     * @param <V>
     */
    public static <V> void sendOn(int command, V value){
        Message msg = new Message<>();
        msg.setCommand(command);
        msg.setValue(value);
        //发送消息给服务端，消息包括自己的socket和发送的数据
        SocketUtil.sendMsg(socket, msg);
    }

    /**
     * 客户端发送消息，并接收服务端返回的消息
     * @param command
     * @param value
     * @param <T>
     * @param <V>
     * @return
     */
    public static <T,V> T sendMsg(int command, V value){
        Message<T,V> msg = new Message<>();
        msg.setCommand(command);
        msg.setValue(value);
        //发送消息给服务端，消息包括自己的socket和发送的数据
        SocketUtil.sendMsg(socket, msg);
        //接收服务端的消息和socket
        return SocketUtil.receiveMsg(socket);
    }

    /**
     * 聊天线程
     */
    public static void chatThread(){
        //接收端线程
        new ReceiveThread(socket).start();
    }
}
